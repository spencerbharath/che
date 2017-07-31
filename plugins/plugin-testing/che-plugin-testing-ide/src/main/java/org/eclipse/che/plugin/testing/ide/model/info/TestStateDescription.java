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

/** Describes state of test. */
public enum TestStateDescription {
  SKIPPED("Skiped"),
  COMPLETED("Completed"),
  NOT_RUN("Not run"),
  RUNNING("Running..."),
  TERMINATED("Terminated"),
  IGNORED("Ignored"),
  FAILED("Failed"),
  ERROR("Error"),
  PASSED("Passed");

  private final String title;

  TestStateDescription(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
