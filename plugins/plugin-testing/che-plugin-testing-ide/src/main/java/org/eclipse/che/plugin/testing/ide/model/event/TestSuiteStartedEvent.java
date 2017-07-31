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

import org.eclipse.che.plugin.testing.ide.messages.TestSuiteStarted;

/** Event which informs about starting test suite. */
public class TestSuiteStartedEvent extends BaseStartEvent {
  public TestSuiteStartedEvent(TestSuiteStarted testSuiteStarted) {
    super(
        getNodeId(testSuiteStarted),
        testSuiteStarted.getSuiteName(),
        getParantNodeId(testSuiteStarted),
        testSuiteStarted.getLocation(),
        getNodeType(testSuiteStarted),
        getNodeArg(testSuiteStarted),
        isNodeRunning(testSuiteStarted));
  }
}
