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
package org.eclipse.che.ide.command.toolbar;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.FontAwesome;
import org.eclipse.che.ide.api.mvp.Presenter;
import org.eclipse.che.ide.command.toolbar.commands.ExecuteCommandPresenter;
import org.eclipse.che.ide.command.toolbar.previews.PreviewsPresenter;
import org.eclipse.che.ide.command.toolbar.processes.ProcessesListPresenter;

/** Presenter for the commands toolbar. */
@Singleton
public class CommandToolbarPresenter implements Presenter, CommandToolbarView.ActionDelegate {

  private final ProcessesListPresenter processesListPresenter;
  private final PreviewsPresenter previewsPresenter;
  private final ExecuteCommandPresenter executeCommandPresenter;
  private final ToolbarButtonsFactory toolbarButtonsFactory;
  private final CommandToolbarView view;
  private ToolbarButton openCommandsPaletteButton;

  @Inject
  public CommandToolbarPresenter(
      CommandToolbarView view,
      ProcessesListPresenter processesListPresenter,
      PreviewsPresenter previewsPresenter,
      ExecuteCommandPresenter executeCommandPresenter,
      ToolbarButtonsFactory toolbarButtonsFactory) {
    this.view = view;
    this.processesListPresenter = processesListPresenter;
    this.previewsPresenter = previewsPresenter;
    this.executeCommandPresenter = executeCommandPresenter;
    this.toolbarButtonsFactory = toolbarButtonsFactory;

    initButtons();

    view.setDelegate(this);
  }

  private void initButtons() {
    final SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
    safeHtmlBuilder.appendHtmlConstant(FontAwesome.LIST);

    openCommandsPaletteButton =
        toolbarButtonsFactory.createOpenPaletteButton(safeHtmlBuilder.toSafeHtml());
  }

  @Override
  public void go(AcceptsOneWidget container) {
    container.setWidget(view);

    executeCommandPresenter.go(view.getCommandsPanelContainer());
    processesListPresenter.go(view.getProcessesListContainer());
    previewsPresenter.go(view.getPreviewUrlsListContainer());

    view.addButton(openCommandsPaletteButton);
  }
}
