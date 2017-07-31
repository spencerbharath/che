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

import org.eclipse.che.plugin.testing.ide.messages.TestFinished;

/** Event when test was finished. */
public class TestFinishedEvent extends TestNodeEvent {

  private final Integer duration;

  public TestFinishedEvent(TestFinished testFinished) {
    super(getNodeId(testFinished), testFinished.getTestName());

    this.duration = testFinished.getTestDuration();
  }

  public Integer getDuration() {
    return duration;
  }
}
