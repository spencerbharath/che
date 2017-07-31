/**
 * ***************************************************************************** Copyright (c)
 * 2016-2017 Rogue Wave Software, Inc. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Rogue Wave Software, Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.composer.ide.communication;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.console.CommandConsoleFactory;
import org.eclipse.che.ide.console.DefaultOutputConsole;
import org.eclipse.che.ide.processes.panel.ProcessesPanelPresenter;
import org.eclipse.che.plugin.composer.ide.ComposerJsonRpcHandler;
import org.eclipse.che.plugin.composer.shared.dto.ComposerOutput;

/**
 * Handler which receives messages from the Composer tool.
 *
 * @author Kaloyan Raev
 */
@Singleton
public class ComposerOutputHandler {

  private final ProcessesPanelPresenter processesPanelPresenter;
  private final AppContext appContext;

  private DefaultOutputConsole outputConsole;

  @Inject
  public ComposerOutputHandler(
      ComposerJsonRpcHandler composerJsonRpcHandler,
      ProcessesPanelPresenter processesPanelPresenter,
      CommandConsoleFactory commandConsoleFactory,
      AppContext appContext) {
    this.processesPanelPresenter = processesPanelPresenter;
    this.appContext = appContext;

    composerJsonRpcHandler.addComposerOutputHandler(this::onComposerOutput);

    outputConsole = (DefaultOutputConsole) commandConsoleFactory.create("Composer");
  }

  private void onComposerOutput(ComposerOutput output) {
    String message = output.getOutput();
    switch (output.getState()) {
      case START:
        processesPanelPresenter.addCommandOutput(appContext.getDevMachine().getId(), outputConsole);
        outputConsole.clearOutputsButtonClicked();
        outputConsole.printText(message, "green");
        break;
      case IN_PROGRESS:
        outputConsole.printText(message);
        break;
      case DONE:
        outputConsole.printText(message, "green");
        break;
      case ERROR:
        outputConsole.printText(message, "red");
        break;
      default:
        break;
    }
  }
}
