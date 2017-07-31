/*
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 */
package org.eclipse.che.ide.editor.synchronization;

import static org.eclipse.che.ide.api.notification.StatusNotification.DisplayMode.EMERGE_MODE;
import static org.eclipse.che.ide.api.notification.StatusNotification.DisplayMode.NOT_EMERGE_MODE;
import static org.eclipse.che.ide.api.notification.StatusNotification.Status.FAIL;
import static org.eclipse.che.ide.api.notification.StatusNotification.Status.SUCCESS;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.ide.api.editor.EditorPartPresenter;
import org.eclipse.che.ide.api.editor.EditorWithAutoSave;
import org.eclipse.che.ide.api.editor.document.Document;
import org.eclipse.che.ide.api.editor.document.DocumentHandle;
import org.eclipse.che.ide.api.editor.document.DocumentStorage;
import org.eclipse.che.ide.api.editor.events.DocumentChangedEvent;
import org.eclipse.che.ide.api.editor.events.DocumentChangedHandler;
import org.eclipse.che.ide.api.editor.text.TextPosition;
import org.eclipse.che.ide.api.editor.texteditor.TextEditor;
import org.eclipse.che.ide.api.event.FileContentUpdateEvent;
import org.eclipse.che.ide.api.event.FileContentUpdateHandler;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.api.resources.File;
import org.eclipse.che.ide.api.resources.VirtualFile;

public class EditorGroupSynchronizationImpl
    implements EditorGroupSynchronization, DocumentChangedHandler, FileContentUpdateHandler {
  private final DocumentStorage documentStorage;
  private final NotificationManager notificationManager;
  private final HandlerRegistration fileContentUpdateHandlerRegistration;
  private final Map<EditorPartPresenter, HandlerRegistration> synchronizedEditors = new HashMap<>();

  private EditorPartPresenter groupLeaderEditor;

  @Inject
  EditorGroupSynchronizationImpl(
      EventBus eventBus, DocumentStorage documentStorage, NotificationManager notificationManager) {
    this.documentStorage = documentStorage;
    this.notificationManager = notificationManager;
    fileContentUpdateHandlerRegistration = eventBus.addHandler(FileContentUpdateEvent.TYPE, this);
  }

  @Override
  public void addEditor(EditorPartPresenter editor) {
    DocumentHandle documentHandle = getDocumentHandleFor(editor);
    if (documentHandle == null) {
      return;
    }

    if (synchronizedEditors.isEmpty()) {
      HandlerRegistration handlerRegistration =
          documentHandle.getDocEventBus().addHandler(DocumentChangedEvent.TYPE, this);
      synchronizedEditors.put(editor, handlerRegistration);
      return;
    }

    EditorPartPresenter groupMember = synchronizedEditors.keySet().iterator().next();
    if ((groupMember instanceof EditorWithAutoSave)
        && !((EditorWithAutoSave) groupMember).isAutoSaveEnabled()) {
      //group can contains unsaved content - we need update content for the editor
      Document editorDocument = documentHandle.getDocument();
      Document groupMemberDocument = getDocumentHandleFor(groupMember).getDocument();

      String oldContent = editorDocument.getContents();
      String groupMemberContent = groupMemberDocument.getContents();

      editorDocument.replace(0, oldContent.length(), groupMemberContent);
    }

    HandlerRegistration handlerRegistration =
        documentHandle.getDocEventBus().addHandler(DocumentChangedEvent.TYPE, this);
    synchronizedEditors.put(editor, handlerRegistration);
  }

  @Override
  public void onActiveEditorChanged(@NotNull EditorPartPresenter activeEditor) {
    groupLeaderEditor = activeEditor;
    resolveAutoSave();
  }

  @Override
  public void removeEditor(EditorPartPresenter editor) {
    HandlerRegistration handlerRegistration = synchronizedEditors.remove(editor);
    if (handlerRegistration != null) {
      handlerRegistration.removeHandler();
    }

    if (groupLeaderEditor == editor) {
      groupLeaderEditor = null;
    }
  }

  @Override
  public void unInstall() {
    synchronizedEditors.values().forEach(HandlerRegistration::removeHandler);

    if (fileContentUpdateHandlerRegistration != null) {
      fileContentUpdateHandlerRegistration.removeHandler();
    }
    groupLeaderEditor = null;
  }

  @Override
  public Set<EditorPartPresenter> getSynchronizedEditors() {
    return synchronizedEditors.keySet();
  }

  @Override
  public void onDocumentChanged(DocumentChangedEvent event) {
    DocumentHandle activeEditorDocumentHandle = getDocumentHandleFor(groupLeaderEditor);
    if (activeEditorDocumentHandle == null
        || !event.getDocument().isSameAs(activeEditorDocumentHandle)) {
      return;
    }

    for (EditorPartPresenter editor : synchronizedEditors.keySet()) {
      if (editor == groupLeaderEditor) {
        continue;
      }

      DocumentHandle documentHandle = getDocumentHandleFor(editor);
      if (documentHandle != null) {
        documentHandle
            .getDocument()
            .replace(event.getOffset(), event.getRemoveCharCount(), event.getText());
      }
    }
  }

  @Override
  public void onFileContentUpdate(final FileContentUpdateEvent event) {
    if (synchronizedEditors.keySet().isEmpty()) {
      return;
    }

    if (groupLeaderEditor == null) {
      groupLeaderEditor = synchronizedEditors.keySet().iterator().next();
      resolveAutoSave();
    }

    final VirtualFile virtualFile = groupLeaderEditor.getEditorInput().getFile();
    if (!event.getFilePath().equals(virtualFile.getLocation().toString())) {
      return;
    }

    documentStorage.getDocument(
        virtualFile,
        new DocumentStorage.DocumentCallback() {

          @Override
          public void onDocumentReceived(final String content) {
            updateContent(content, event.getModificationStamp(), virtualFile);
          }

          @Override
          public void onDocumentLoadFailure(final Throwable caught) {
            notificationManager.notify(
                "",
                "Can not to update content for the file " + virtualFile.getDisplayName(),
                FAIL,
                EMERGE_MODE);
          }
        });
  }

  private void updateContent(
      String newContent, String eventModificationStamp, VirtualFile virtualFile) {
    final DocumentHandle documentHandle = getDocumentHandleFor(groupLeaderEditor);
    if (documentHandle == null) {
      return;
    }

    final Document document = documentHandle.getDocument();
    final String oldContent = document.getContents();
    final TextPosition cursorPosition = document.getCursorPosition();

    if (!(virtualFile instanceof File)) {
      replaceContent(document, newContent, oldContent, cursorPosition);
      return;
    }

    final File file = (File) virtualFile;
    final String currentStamp = file.getModificationStamp();

    if (eventModificationStamp == null && !Objects.equals(newContent, oldContent)) {
      replaceContent(document, newContent, oldContent, cursorPosition);
      return;
    }

    if (!Objects.equals(eventModificationStamp, currentStamp)) {
      replaceContent(document, newContent, oldContent, cursorPosition);

      notificationManager.notify(
          "External operation",
          "File '" + file.getName() + "' is updated",
          SUCCESS,
          NOT_EMERGE_MODE);
    }
  }

  private void replaceContent(
      Document document, String newContent, String oldContent, TextPosition cursorPosition) {
    document.replace(0, oldContent.length(), newContent);
    document.setCursorPosition(cursorPosition);
  }

  @Nullable
  private DocumentHandle getDocumentHandleFor(EditorPartPresenter editor) {
    if (editor == null || !(editor instanceof TextEditor)) {
      return null;
    }
    return ((TextEditor) editor).getDocument().getDocumentHandle();
  }

  private void resolveAutoSave() {
    synchronizedEditors.keySet().forEach(this::resolveAutoSaveFor);
  }

  private void resolveAutoSaveFor(EditorPartPresenter editor) {
    if (!(editor instanceof EditorWithAutoSave)) {
      return;
    }

    EditorWithAutoSave editorWithAutoSave = (EditorWithAutoSave) editor;
    if (editorWithAutoSave == groupLeaderEditor) {
      editorWithAutoSave.enableAutoSave();
      return;
    }

    editorWithAutoSave.disableAutoSave();
  }
}
