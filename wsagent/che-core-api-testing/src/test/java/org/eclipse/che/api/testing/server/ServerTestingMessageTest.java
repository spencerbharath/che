/**
 * ***************************************************************************** Copyright (c)
 * 2012-2017 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.api.testing.server;

import org.eclipse.che.api.testing.server.messages.ServerTestingMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

/** */
public class ServerTestingMessageTest {

  @Test
  public void testMessageShouldUnescapeAttributes() throws Exception {
    String out =
        "@@<{\"name\":\"testFailed\", \"attributes\":{\"name\":\"Test.testMethod\", \"details\":\"java.lang.Exception: Test!n\tat org.testng.che.tests.ListeneterTest.testName(ListeneterTest.java:71)!n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)!n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)!n\", \"message\":\"Test\"}}>";
    ServerTestingMessage parse = ServerTestingMessage.parse(out);
    Assert.assertEquals(
        parse.asJsonString(),
        "{\"name\":\"testFailed\",\"attributes\":{\"name\":\"Test.testMethod\",\"details\":\"java.lang.Exception: Test\\n\\tat org.testng.che.tests.ListeneterTest.testName(ListeneterTest.java:71)\\n\\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\\n\\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\\n\",\"message\":\"Test\"}}");
  }
}
