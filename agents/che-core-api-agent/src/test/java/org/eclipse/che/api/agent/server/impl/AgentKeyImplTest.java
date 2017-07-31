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
package org.eclipse.che.api.agent.server.impl;

import static org.testng.AssertJUnit.assertEquals;

import org.eclipse.che.api.agent.shared.model.impl.AgentKeyImpl;
import org.testng.annotations.Test;

/** @author Anatolii Bazko */
public class AgentKeyImplTest {

  @Test
  public void testAgentKeyWithNameAndVersion() {
    AgentKeyImpl agentKey = AgentKeyImpl.parse("id:1");

    assertEquals(agentKey.getId(), "id");
    assertEquals(agentKey.getVersion(), "1");
  }

  @Test
  public void testParseAgentKeyWithId() {
    AgentKeyImpl agentKey = AgentKeyImpl.parse("id");

    assertEquals(agentKey.getId(), "id");
    assertEquals(agentKey.getVersion(), "latest");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testParseAgentKeyFails() {
    AgentKeyImpl.parse("id:1:2");
  }
}
