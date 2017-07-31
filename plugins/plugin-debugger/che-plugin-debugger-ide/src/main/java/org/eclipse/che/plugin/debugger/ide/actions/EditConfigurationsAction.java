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
package org.eclipse.che.plugin.debugger.ide.actions;

import static org.eclipse.che.ide.workspace.perspectives.project.ProjectPerspective.PROJECT_PERSPECTIVE_ID;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Collections;
import org.eclipse.che.ide.api.action.AbstractPerspectiveAction;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.machine.MachineResources;
import org.eclipse.che.plugin.debugger.ide.DebuggerLocalizationConstant;
import org.eclipse.che.plugin.debugger.ide.configuration.EditDebugConfigurationsPresenter;

/**
 * Action for opening dialog for managing debug configurations.
 *
 * @author Artem Zatsarynnyi
 */
@Singleton
public class EditConfigurationsAction extends AbstractPerspectiveAction {

  private final EditDebugConfigurationsPresenter editCommandsPresenter;

  @Inject
  public EditConfigurationsAction(
      EditDebugConfigurationsPresenter editDebugConfigurationsPresenter,
      DebuggerLocalizationConstant localizationConstant,
      MachineResources resources) {
    super(
        Collections.singletonList(PROJECT_PERSPECTIVE_ID),
        localizationConstant.editDebugConfigurationsActionTitle(),
        localizationConstant.editDebugConfigurationsActionDescription(),
        null,
        resources.editCommands());
    this.editCommandsPresenter = editDebugConfigurationsPresenter;
  }

  @Override
  public void updateInPerspective(ActionEvent e) {}

  @Override
  public void actionPerformed(ActionEvent e) {
    editCommandsPresenter.show();
  }
}
