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

/** Represents test/suite state info. */
public interface TestStateInfo {

  /**
   * Final(immutable) state, ie this state will not changed.
   *
   * @return
   */
  boolean isFinal();

  /** @return Test/Suite is running */
  boolean isInProgress();

  /** @return this Test/Suite has problem, which user can be notified */
  boolean isProblem();

  /** @return Test/Suite was launched */
  boolean wasLaunched();

  /** @return this Test/Suite was terminated by user */
  boolean wasTerminated();

  /** Describe test state type */
  TestStateDescription getDescription();
}
