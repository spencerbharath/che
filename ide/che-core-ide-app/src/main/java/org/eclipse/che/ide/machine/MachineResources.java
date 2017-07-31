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
package org.eclipse.che.ide.machine;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import org.vectomatic.dom.svg.ui.SVGResource;

/** Interface for machine resources */
public interface MachineResources extends ClientBundle {
  @Source({"machine.css", "org/eclipse/che/ide/api/ui/style.css"})
  MachineResources.Css getCss();

  /** Returns the icon for clear console button. */
  @Source("console/clear-logs.svg")
  SVGResource clear();

  /** Returns the new icon for devmachine. */
  @Source("cube.svg")
  SVGResource devMachine();

  @Source("process/output-icon.svg")
  SVGResource output();

  @Source("process/terminal-icon.svg")
  SVGResource terminal();

  @Source("process/terminal-tree-icon.svg")
  SVGResource terminalTreeIcon();

  @Source("process/add-terminal.svg")
  SVGResource addTerminalIcon();

  @Source("process/re-run.svg")
  SVGResource reRunIcon();

  @Source("process/stop.svg")
  SVGResource stopIcon();

  @Source("process/clear-outputs.svg")
  SVGResource clearOutputsIcon();

  @Source("process/scroll-to-bottom.svg")
  SVGResource scrollToBottomIcon();

  @Source("process/line-wrap.svg")
  SVGResource lineWrapIcon();

  @Source("edit-commands.svg")
  SVGResource editCommands();

  /** Returns the icon for 'Custom' command type. */
  @Source("custom-command-type.svg")
  SVGResource customCommandType();

  @Source("project-perspective.svg")
  SVGResource projectPerspective();

  /** CssResource for the image viewer. */
  public interface Css extends CssResource {
    String processTree();

    String processTreeNode();

    String commandTreeNode();

    String machineStatus();

    String machineStatusRunning();

    String machineStatusPausedLeft();

    String machineStatusPausedRight();

    String nameLabel();

    String processIconPanel();

    String processIcon();

    String processBadge();

    String badgeVisible();

    String newTerminalButton();

    String sshButton();

    String processNavigation();

    String machineMonitors();

    /** Returns the CSS class name for close button of process in 'Consoles' panel. */
    String processesPanelCloseButtonForProcess();

    /** Returns the CSS class name for stop button of process in 'Consoles' panel. */
    String processesPanelStopButtonForProcess();

    String hideStopButton();
  }
}
