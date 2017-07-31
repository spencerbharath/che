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
package org.eclipse.che.plugin.gdb.server.parser;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/** @author Anatoliy Bazko */
public class GdbPTypeTest {

  @Test
  public void testParse() throws Exception {
    GdbOutput gdbOutput = GdbOutput.of("type = int\n");

    GdbPType gdbPType = GdbPType.parse(gdbOutput);

    assertEquals(gdbPType.getType(), "int");
  }
}
