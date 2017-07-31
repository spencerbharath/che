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
package org.eclipse.che.ide.command.editor;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.google.inject.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/** Tests for {@link CommandEditorProvider}. */
@RunWith(MockitoJUnitRunner.class)
public class CommandEditorProviderTest {

  @Mock private EditorMessages editorMessages;

  @Mock private Provider<CommandEditor> editorProvider;

  @InjectMocks private CommandEditorProvider provider;

  @Test
  public void shouldReturnId() throws Exception {
    assertThat(provider.getId()).isNotNull();
    assertThat(provider.getId()).isNotEmpty();
  }

  @Test
  public void shouldReturnDescriptions() throws Exception {
    provider.getDescription();

    verify(editorMessages).editorDescription();
  }

  @Test
  public void shouldReturnEditor() throws Exception {
    provider.getEditor();

    verify(editorProvider).get();
  }
}
