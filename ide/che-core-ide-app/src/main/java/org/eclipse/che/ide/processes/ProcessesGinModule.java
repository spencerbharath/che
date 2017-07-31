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
package org.eclipse.che.ide.processes;

import static org.eclipse.che.ide.processes.ProcessTreeNode.ProcessNodeType.COMMAND_NODE;
import static org.eclipse.che.ide.processes.ProcessTreeNode.ProcessNodeType.MACHINE_NODE;
import static org.eclipse.che.ide.processes.ProcessTreeNode.ProcessNodeType.TERMINAL_NODE;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.inject.client.multibindings.GinMapBinder;
import com.google.inject.Singleton;
import org.eclipse.che.ide.processes.actions.ConsoleTreeContextMenuFactory;
import org.eclipse.che.ide.processes.panel.ProcessesPanelView;
import org.eclipse.che.ide.processes.panel.ProcessesPanelViewImpl;

/** GIN module for configuring process panel. */
public class ProcessesGinModule extends AbstractGinModule {
  @Override
  protected void configure() {
    bind(ProcessesPanelView.class).to(ProcessesPanelViewImpl.class).in(Singleton.class);
    install(new GinFactoryModuleBuilder().build(ConsoleTreeContextMenuFactory.class));

    GinMapBinder.newMapBinder(binder(), String.class, ProcessTreeNodeRenderStrategy.class)
        .addBinding(COMMAND_NODE.getStringValue())
        .to(CommandNodeRenderStrategy.class);
    GinMapBinder.newMapBinder(binder(), String.class, ProcessTreeNodeRenderStrategy.class)
        .addBinding(MACHINE_NODE.getStringValue())
        .to(MachineNodeRenderStrategy.class);
    GinMapBinder.newMapBinder(binder(), String.class, ProcessTreeNodeRenderStrategy.class)
        .addBinding(TERMINAL_NODE.getStringValue())
        .to(TerminalNodeRenderStrategy.class);
  }
}
