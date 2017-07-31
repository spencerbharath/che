/**
 * ***************************************************************************** Copyright (c)
 * 2016-2017 Rogue Wave Software, Inc. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Rogue Wave Software, Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.composer.server.projecttype;

import static org.eclipse.che.plugin.composer.shared.Constants.COMPOSER_PROJECT_TYPE_ID;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.inject.Inject;
import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.ForbiddenException;
import org.eclipse.che.api.core.NotFoundException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.project.server.FolderEntry;
import org.eclipse.che.api.project.server.ProjectRegistry;
import org.eclipse.che.api.project.server.handlers.ProjectInitHandler;
import org.eclipse.che.plugin.composer.server.executor.ComposerCommandExecutor;

/** @author Kaloyan Raev */
public class ComposerProjectInitializer implements ProjectInitHandler {

  private ComposerCommandExecutor commandExecutor;

  @Inject
  public ComposerProjectInitializer(ComposerCommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @Override
  public void onProjectInitialized(ProjectRegistry registry, FolderEntry projectFolder)
      throws ServerException, ForbiddenException, ConflictException, NotFoundException {
    String[] commandLine = {"composer", "install"};
    File workDir = projectFolder.getVirtualFile().toIoFile();
    try {
      commandExecutor.execute(commandLine, workDir);
    } catch (TimeoutException | IOException | InterruptedException e) {
      throw new ServerException(e);
    }
  }

  @Override
  public String getProjectType() {
    return COMPOSER_PROJECT_TYPE_ID;
  }
}
