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
import java.util.Collections;
import org.eclipse.che.ide.api.action.AbstractPerspectiveAction;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.debug.BreakpointManager;
import org.eclipse.che.plugin.debugger.ide.DebuggerLocalizationConstant;
import org.eclipse.che.plugin.debugger.ide.DebuggerResources;

/**
 * Action which allows remove all breakpoints
 *
 * @author Mykola Morhun
 */
public class DeleteAllBreakpointsAction extends AbstractPerspectiveAction {

  private final BreakpointManager breakpointManager;

  @Inject
  public DeleteAllBreakpointsAction(
      BreakpointManager breakpointManager,
      DebuggerLocalizationConstant locale,
      DebuggerResources resources) {
    super(
        Collections.singletonList(PROJECT_PERSPECTIVE_ID),
        locale.deleteAllBreakpoints(),
        locale.deleteAllBreakpointsDescription(),
        null,
        resources.deleteAllBreakpoints());
    this.breakpointManager = breakpointManager;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    breakpointManager.deleteAllBreakpoints();
  }

  @Override
  public void updateInPerspective(ActionEvent event) {
    event.getPresentation().setEnabled(!breakpointManager.getBreakpointList().isEmpty());
  }
}
