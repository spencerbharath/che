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
package org.eclipse.che.ide.macro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.web.bindery.event.shared.EventBus;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.che.api.core.model.machine.Machine;
import org.eclipse.che.api.core.model.machine.MachineRuntimeInfo;
import org.eclipse.che.api.core.model.machine.Server;
import org.eclipse.che.api.machine.shared.Constants;
import org.eclipse.che.api.promises.client.Operation;
import org.eclipse.che.api.promises.client.OperationException;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.machine.DevMachine;
import org.eclipse.che.ide.api.macro.BaseMacro;
import org.eclipse.che.ide.api.macro.Macro;
import org.eclipse.che.ide.api.macro.MacroRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

/**
 * Unit tests for the {@link ServerProtocolMacro}
 *
 * @author Vlad Zhukovskyi
 */
@RunWith(GwtMockitoTestRunner.class)
public class ServerProtocolMacroTest {

  public static final String WS_AGENT_PORT = Constants.WS_AGENT_PORT; // 4401/tcp
  public static final String PORT = "1234";
  public static final String ADDRESS = "127.0.0.1" + ":" + PORT;
  public static final String PROTOCOL = "protocol";

  @Mock private MacroRegistry macroRegistry;

  @Mock private EventBus eventBus;

  @Mock private AppContext appContext;

  @Mock private DevMachine devMachine;

  @Mock private Machine machine;

  @Mock private MachineRuntimeInfo machineRuntimeInfo;

  @Mock private Server server;

  private ServerProtocolMacro provider;

  @Before
  public void setUp() throws Exception {
    provider = new ServerProtocolMacro(macroRegistry, eventBus, appContext);

    registerProvider();
  }

  @Test
  public void getMacros() throws Exception {
    final Set<Macro> macros = provider.getMacros(devMachine);

    assertEquals(macros.size(), 2);

    final Iterator<Macro> iterator = macros.iterator();

    final Macro provider1 = iterator.next();

    assertTrue(provider1 instanceof BaseMacro);
    assertEquals(provider1.getName(), ServerProtocolMacro.KEY.replace("%", WS_AGENT_PORT));

    provider1
        .expand()
        .then(
            new Operation<String>() {
              @Override
              public void apply(String address) throws OperationException {
                assertEquals(address, PROTOCOL);
              }
            });

    final Macro provider2 = iterator.next();

    assertTrue(provider2 instanceof BaseMacro);
    assertEquals(
        provider2.getName(),
        ServerProtocolMacro.KEY.replace(
            "%", WS_AGENT_PORT.substring(0, WS_AGENT_PORT.length() - 4)));

    provider2
        .expand()
        .then(
            new Operation<String>() {
              @Override
              public void apply(String address) throws OperationException {
                assertEquals(address, PROTOCOL);
              }
            });
  }

  protected void registerProvider() {
    when(devMachine.getDescriptor()).thenReturn(machine);
    when(machine.getRuntime()).thenReturn(machineRuntimeInfo);
    doReturn(Collections.<String, Server>singletonMap(WS_AGENT_PORT, server))
        .when(machineRuntimeInfo)
        .getServers();
    when(server.getAddress()).thenReturn(ADDRESS);
    when(server.getProtocol()).thenReturn(PROTOCOL);
  }
}
