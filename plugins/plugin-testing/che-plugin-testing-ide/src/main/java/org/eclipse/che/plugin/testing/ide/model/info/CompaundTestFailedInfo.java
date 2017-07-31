/**
 * ***************************************************************************** Copyright (c)
 * 2012-2017 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.testing.ide.model.info;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.che.plugin.testing.ide.model.Printer;

/** Describes information about failed tests. */
public class CompaundTestFailedInfo extends TestFailedInfo {

  private final List<TestFailedInfo> failedTests = new ArrayList<>();

  public CompaundTestFailedInfo(String message, String stackTrace) {
    super(message, stackTrace);
  }

  public void addFailedTest(TestFailedInfo failedInfo) {
    failedTests.add(failedInfo);
  }

  @Override
  public void print(Printer printer) {
    for (TestFailedInfo failedTest : failedTests) {
      failedTest.print(printer);
    }
  }
}
