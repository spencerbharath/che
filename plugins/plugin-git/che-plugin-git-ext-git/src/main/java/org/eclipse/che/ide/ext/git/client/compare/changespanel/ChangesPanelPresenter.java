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
package org.eclipse.che.ide.ext.git.client.compare.changespanel;

import static org.eclipse.che.ide.api.notification.StatusNotification.DisplayMode.NOT_EMERGE_MODE;
import static org.eclipse.che.ide.api.notification.StatusNotification.Status.FAIL;
import static org.eclipse.che.ide.ext.git.client.compare.changespanel.ViewMode.LIST;
import static org.eclipse.che.ide.ext.git.client.compare.changespanel.ViewMode.TREE;

import com.google.inject.Inject;
import java.util.Map;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.ext.git.client.GitLocalizationConstant;
import org.eclipse.che.ide.ext.git.client.compare.ComparePresenter;
import org.eclipse.che.ide.ext.git.client.compare.FileStatus.Status;

/**
 * Presenter for displaying list of changed files.
 *
 * @author Igor Vinokur
 * @author Vlad Zhukovskyi
 */
public class ChangesPanelPresenter implements ChangesPanelView.ActionDelegate {

  private final ChangesPanelView view;
  private final AppContext appContext;
  private final NotificationManager notificationManager;
  private final ComparePresenter comparePresenter;
  private final GitLocalizationConstant locale;

  private Map<String, Status> changedFiles;
  private ViewMode viewMode;

  @Inject
  public ChangesPanelPresenter(
      GitLocalizationConstant locale,
      ChangesPanelView view,
      AppContext appContext,
      NotificationManager notificationManager,
      ComparePresenter comparePresenter) {
    this.locale = locale;
    this.view = view;
    this.appContext = appContext;
    this.notificationManager = notificationManager;
    this.comparePresenter = comparePresenter;
    this.view.setDelegate(this);
    this.viewMode = TREE;
  }

  /**
   * Show panel with changed files. If empty map with changed files is received, all buttons would
   * be disabled.
   *
   * @param changedFiles Map with files and their status
   */
  public void show(Map<String, Status> changedFiles) {
    this.changedFiles = changedFiles;
    if (changedFiles.isEmpty()) {
      view.setTextToChangeViewModeButton(locale.changeListRowListViewButtonText());
      view.setEnabledChangeViewModeButton(false);
      view.setEnableExpandCollapseButtons(false);
      view.resetPanelState();
    } else {
      view.setEnabledChangeViewModeButton(true);
      view.setEnableExpandCollapseButtons(viewMode == TREE);
      viewChangedFiles();
    }
  }

  public ChangesPanelView getView() {
    return view;
  }

  @Override
  public void onFileNodeDoubleClicked(String path, final Status status) {
    appContext
        .getRootProject()
        .getFile(path)
        .then(
            file -> {
              if (file.isPresent()) {
                comparePresenter.showCompareWithLatest(file.get(), status, "HEAD");
              }
            })
        .catchError(
            error -> {
              notificationManager.notify(error.getMessage(), FAIL, NOT_EMERGE_MODE);
            });
  }

  @Override
  public void onChangeViewModeButtonClicked() {
    viewMode = viewMode == TREE ? LIST : TREE;
    view.setEnableExpandCollapseButtons(viewMode == TREE);
    viewChangedFiles();
  }

  @Override
  public void onExpandButtonClicked() {
    view.expandAllDirectories();
  }

  @Override
  public void onCollapseButtonClicked() {
    view.collapseAllDirectories();
  }

  private void viewChangedFiles() {
    view.viewChangedFiles(changedFiles, viewMode);
    view.setTextToChangeViewModeButton(
        viewMode == TREE
            ? locale.changeListRowListViewButtonText()
            : locale.changeListGroupByDirectoryButtonText());
  }
}
