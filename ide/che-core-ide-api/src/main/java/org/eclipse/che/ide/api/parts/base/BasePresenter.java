/*
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 */
package org.eclipse.che.ide.api.parts.base;

import org.eclipse.che.ide.api.parts.AbstractPartPresenter;
import org.eclipse.che.ide.api.parts.PartStack;

/**
 * Base presenter for parts that support minimizing by part toolbar button.
 *
 * @author Evgen Vidolob
 */
public abstract class BasePresenter extends AbstractPartPresenter implements BaseActionDelegate {

  @Override
  public void onToggleMaximize() {
    if (partStack != null) {
      if (partStack.getPartStackState() == PartStack.State.MAXIMIZED) {
        partStack.restore();
      } else {
        partStack.maximize();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void onMinimize() {
    if (partStack != null) {
      partStack.minimize();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void onActivate() {
    partStack.setActivePart(this);
  }

  @Override
  public void onPartMenu(int mouseX, int mouseY) {
    partStack.showPartMenu(mouseX, mouseY);
  }
}
