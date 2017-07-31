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
package org.eclipse.che.ide.workspace.events;

import com.google.gwt.inject.client.AbstractGinModule;

public class WorkspaceEventsModule extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(EnvironmentOutputHandler.class).asEagerSingleton();
    bind(EnvironmentStatusEventHandler.class).asEagerSingleton();
    bind(WorkspaceAgentOutputHandler.class).asEagerSingleton();
    bind(WorkspaceStatusEventHandler.class).asEagerSingleton();
  }
}
