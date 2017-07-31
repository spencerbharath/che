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
package org.eclipse.che.ide.ext.git.client.compare.revisionslist;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Collections.singletonList;
import static org.eclipse.che.api.git.shared.DiffType.NAME_STATUS;
import static org.eclipse.che.ide.api.notification.StatusNotification.DisplayMode.NOT_EMERGE_MODE;
import static org.eclipse.che.ide.api.notification.StatusNotification.Status.FAIL;
import static org.eclipse.che.ide.ext.git.client.compare.FileStatus.defineStatus;
import static org.eclipse.che.ide.util.ExceptionUtils.getErrorCode;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javax.validation.constraints.NotNull;
import org.eclipse.che.api.core.ErrorCodes;
import org.eclipse.che.api.git.shared.Revision;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.dialogs.DialogFactory;
import org.eclipse.che.ide.api.git.GitServiceClient;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.api.resources.File;
import org.eclipse.che.ide.api.resources.Project;
import org.eclipse.che.ide.ext.git.client.GitLocalizationConstant;
import org.eclipse.che.ide.ext.git.client.compare.ComparePresenter;
import org.eclipse.che.ide.resource.Path;

/**
 * Presenter for displaying list of revisions for comparing selected with local changes.
 *
 * @author Igor Vinokur
 * @author Vlad Zhukovskyi
 */
@Singleton
public class RevisionListPresenter implements RevisionListView.ActionDelegate {
  private final ComparePresenter comparePresenter;
  private final DialogFactory dialogFactory;
  private final RevisionListView view;
  private final GitServiceClient service;
  private final GitLocalizationConstant locale;
  private final AppContext appContext;
  private final NotificationManager notificationManager;

  private Revision selectedRevision;
  private Project project;
  private Path selectedFilePath;

  @Inject
  public RevisionListPresenter(
      RevisionListView view,
      ComparePresenter comparePresenter,
      GitServiceClient service,
      GitLocalizationConstant locale,
      NotificationManager notificationManager,
      DialogFactory dialogFactory,
      AppContext appContext) {
    this.view = view;
    this.comparePresenter = comparePresenter;
    this.dialogFactory = dialogFactory;
    this.service = service;
    this.locale = locale;
    this.appContext = appContext;
    this.notificationManager = notificationManager;

    this.view.setDelegate(this);
  }

  /** Open dialog and shows revisions to compare. */
  public void showRevisions(Project project, File selectedFile) {
    this.project = project;

    checkState(
        project.getLocation().isPrefixOf(selectedFile.getLocation()),
        "Given selected file is not descendant of given project");

    selectedFilePath =
        selectedFile
            .getLocation()
            .removeFirstSegments(project.getLocation().segmentCount())
            .removeTrailingSeparator();
    getRevisions();
  }

  /** {@inheritDoc} */
  @Override
  public void onCloseClicked() {
    view.close();
  }

  /** {@inheritDoc} */
  @Override
  public void onCompareClicked() {
    compare();
  }

  /** {@inheritDoc} */
  @Override
  public void onRevisionUnselected() {
    selectedRevision = null;
    view.setEnableCompareButton(false);
    view.setDescription(locale.viewCompareRevisionFullDescriptionEmptyMessage());
  }

  /** {@inheritDoc} */
  @Override
  public void onRevisionSelected(@NotNull Revision revision) {
    selectedRevision = revision;

    view.setEnableCompareButton(true);
  }

  /** {@inheritDoc} */
  @Override
  public void onRevisionDoubleClicked() {
    compare();
  }

  /** Get list of revisions. */
  private void getRevisions() {
    service
        .log(project.getLocation(), new Path[] {selectedFilePath}, -1, -1, false)
        .then(
            log -> {
              view.setRevisions(log.getCommits());
              view.showDialog();
            })
        .catchError(
            error -> {
              if (getErrorCode(error.getCause()) == ErrorCodes.INIT_COMMIT_WAS_NOT_PERFORMED) {
                dialogFactory
                    .createMessageDialog(
                        locale.compareWithRevisionTitle(), locale.initCommitWasNotPerformed(), null)
                    .show();
              } else {
                notificationManager.notify(locale.logFailed(), FAIL, NOT_EMERGE_MODE);
              }
            });
  }

  private void compare() {
    service
        .diff(
            project.getLocation(),
            singletonList(selectedFilePath.toString()),
            NAME_STATUS,
            false,
            0,
            selectedRevision.getId(),
            false)
        .then(
            diff -> {
              if (diff.isEmpty()) {
                dialogFactory
                    .createMessageDialog(
                        locale.compareMessageIdenticalContentTitle(),
                        locale.compareMessageIdenticalContentText(),
                        null)
                    .show();
              } else {
                appContext
                    .getRootProject()
                    .getFile(diff.substring(2))
                    .then(
                        file -> {
                          if (file.isPresent()) {
                            comparePresenter.showCompareWithLatest(
                                file.get(),
                                defineStatus(diff.substring(0, 1)),
                                selectedRevision.getId());
                          }
                        });
              }
            })
        .catchError(
            arg -> {
              notificationManager.notify(locale.diffFailed(), FAIL, NOT_EMERGE_MODE);
            });
  }
}
