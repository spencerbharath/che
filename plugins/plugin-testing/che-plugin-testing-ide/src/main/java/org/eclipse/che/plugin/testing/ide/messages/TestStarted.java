/**
 * ***************************************************************************** Copyright (c)
 * 2012-2017 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.testing.ide.messages;

/** Data class represents test started message. */
public class TestStarted extends BaseTestMessage {

  TestStarted() {}

  @Override
  public void visit(TestingMessageVisitor visitor) {
    visitor.visitTestStarted(this);
  }

  public String getLocation() {
    return getAttributeValue("locationHint");
  }

  public boolean isConfig() {
    String config = getAttributeValue("config");
    return !(config == null || config.isEmpty()) && Boolean.parseBoolean(config);
  }
}
