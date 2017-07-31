/**
 * ***************************************************************************** Copyright (c)
 * 2012-2017 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.testing.ide.view2.navigation.nodes;

import com.google.inject.assistedinject.Assisted;
import javax.inject.Inject;
import org.eclipse.che.api.promises.client.PromiseProvider;
import org.eclipse.che.plugin.testing.ide.TestResources;
import org.eclipse.che.plugin.testing.ide.model.TestRootState;
import org.eclipse.che.plugin.testing.ide.model.TestState;

/** */
public class TestRootNode extends TestStateNode {

  private final TestRootState testState;

  @Inject
  public TestRootNode(
      PromiseProvider promiseProvider,
      TestResources testResources,
      @Assisted TestRootState testState) {
    super(promiseProvider, testResources, testState);
    this.testState = testState;
  }

  @Override
  public String getName() {
    return testState.getPresentation() == null ? testState.getName() : testState.getPresentation();
  }

  @Override
  public TestState getTestState() {
    return testState;
  }
}
