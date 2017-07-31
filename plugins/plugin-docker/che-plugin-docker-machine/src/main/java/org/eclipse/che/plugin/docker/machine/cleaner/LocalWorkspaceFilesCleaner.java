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
package org.eclipse.che.plugin.docker.machine.cleaner;

import static java.lang.System.getenv;
import static org.eclipse.che.commons.lang.IoUtil.deleteRecursive;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.io.File;
import java.io.IOException;
import org.eclipse.che.api.core.model.workspace.Workspace;
import org.eclipse.che.api.workspace.server.WorkspaceFilesCleaner;
import org.eclipse.che.plugin.docker.machine.local.node.provider.LocalWorkspaceFolderPathProvider;

/**
 * Local implementation of the {@link WorkspaceFilesCleaner}.
 *
 * @author Alexander Andrienko
 * @author Igor Vinokur
 */
@Singleton
public class LocalWorkspaceFilesCleaner implements WorkspaceFilesCleaner {

  private final LocalWorkspaceFolderPathProvider workspaceFolderPathProvider;

  @Inject(optional = true)
  @Named("host.projects.root")
  private String hostProjectsFolder;

  @Inject
  public LocalWorkspaceFilesCleaner(LocalWorkspaceFolderPathProvider workspaceFolderPathProvider) {
    this.workspaceFolderPathProvider = workspaceFolderPathProvider;
  }

  @Override
  public void clear(Workspace workspace) throws IOException {
    String workspacePath =
        workspaceFolderPathProvider.getPathByName(workspace.getConfig().getName());
    if (!workspacePath.equals(hostProjectsFolder)) {
      // Remove the workspace folder located in the Che instance docker container.
      deleteRecursive(new File(workspacePath.replace(getenv("CHE_INSTANCE"), "")));
    }
  }
}
