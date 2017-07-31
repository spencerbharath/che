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

/** Describes information about terminated test. */
public class TerminatedInfo extends AbstractTestStateInfo {

  public static final TerminatedInfo INSTANCE = new TerminatedInfo();

  private TerminatedInfo() {}

  @Override
  public boolean isFinal() {
    return true;
  }

  @Override
  public boolean isInProgress() {
    return false;
  }

  @Override
  public boolean isProblem() {
    return true;
  }

  @Override
  public boolean wasLaunched() {
    return true;
  }

  @Override
  public boolean wasTerminated() {
    return true;
  }

  @Override
  public TestStateDescription getDescription() {
    return TestStateDescription.TERMINATED;
  }
}
