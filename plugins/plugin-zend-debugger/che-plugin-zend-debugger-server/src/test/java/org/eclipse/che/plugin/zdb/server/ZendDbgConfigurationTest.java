/**
 * ***************************************************************************** Copyright (c) 2016
 * Rogue Wave Software, Inc. All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Rogue Wave Software, Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.zdb.server;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.testng.Assert.assertTrue;

import org.eclipse.che.api.debug.shared.model.DebuggerInfo;
import org.eclipse.che.plugin.zdb.server.connection.ZendDbgSettings;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Simple Zend Debugger configuration tests..
 *
 * @author Bartlomiej Laczkowski
 */
public class ZendDbgConfigurationTest {

  static final String DEBUG_HOST = "10.10.10.10";
  static final int DEBUG_PORT = 10000;

  private ZendDebugger debugger;

  @BeforeMethod
  public void setUp() throws Exception {
    ZendDbgSettings dbgSettings = new ZendDbgSettings(DEBUG_PORT, DEBUG_HOST, true, false);
    debugger = new ZendDebugger(dbgSettings, null, null);
  }

  @Test(
    groups = {"zendDbg"},
    dependsOnGroups = {"checkPHP"}
  )
  public void testGetInfo() throws Exception {
    DebuggerInfo info = debugger.getInfo();
    assertTrue(info.getFile() == null);
    assertTrue(isNullOrEmpty(info.getVersion()));
    assertTrue(info.getName().equals("Zend Debugger"));
    assertTrue(info.getPid() == 0);
    assertTrue(info.getHost().equals(DEBUG_HOST));
    assertTrue(info.getPort() == DEBUG_PORT);
  }
}
