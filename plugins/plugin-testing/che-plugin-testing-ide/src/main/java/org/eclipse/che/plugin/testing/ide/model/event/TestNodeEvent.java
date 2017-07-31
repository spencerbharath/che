/**
 * ***************************************************************************** Copyright (c)
 * 2012-2017 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.testing.ide.model.event;

import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.plugin.testing.ide.messages.ClientTestingMessage;

/** Core event for the test events. */
public abstract class TestNodeEvent {
  private String id;
  private String name;

  public TestNodeEvent(String id, String name) {
    this.id = id;
    this.name = name;
  }

  protected static String getNodeId(ClientTestingMessage message) {
    return message.getAttributes().get("nodeId");
  }

  @Nullable
  public String getId() {
    return id;
  }

  @Nullable
  public String getName() {
    return name;
  }
}
