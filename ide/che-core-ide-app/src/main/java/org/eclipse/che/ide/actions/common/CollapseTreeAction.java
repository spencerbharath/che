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
package org.eclipse.che.ide.actions.common;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.che.ide.api.action.Action;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.data.tree.TreeExpander;

/**
 * Base tree collapse action which consumes instance of {@link TreeExpander}.
 *
 * @author Vlad Zhukovskyi
 * @see TreeExpander
 * @since 5.0.0
 */
public abstract class CollapseTreeAction extends Action {

  public abstract TreeExpander getTreeExpander();

  public CollapseTreeAction() {
    super("Collapse All");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    final TreeExpander treeExpander = getTreeExpander();

    checkNotNull(treeExpander);

    if (!treeExpander.isCollapseEnabled()) {
      return;
    }

    treeExpander.collapseTree();
  }

  @Override
  public void update(ActionEvent e) {
    final TreeExpander treeExpander = getTreeExpander();

    e.getPresentation().setEnabledAndVisible(treeExpander.isCollapseEnabled());
  }
}
