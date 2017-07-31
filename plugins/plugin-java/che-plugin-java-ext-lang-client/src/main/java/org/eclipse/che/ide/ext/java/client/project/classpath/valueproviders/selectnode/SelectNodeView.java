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
package org.eclipse.che.ide.ext.java.client.project.classpath.valueproviders.selectnode;

import com.google.inject.ImplementedBy;
import java.util.List;
import org.eclipse.che.ide.api.data.tree.Node;
import org.eclipse.che.ide.api.mvp.View;
import org.eclipse.che.ide.ext.java.client.project.classpath.valueproviders.selectnode.interceptors.ClasspathNodeInterceptor;

/**
 * Represents the structure of the workspace. It needs for choosing a needed node.
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(SelectNodeViewImpl.class)
public interface SelectNodeView extends View<SelectNodeView.ActionDelegate> {
  /** Needs for delegate some function into SelectPath view. */
  interface ActionDelegate {
    /** Sets selected node. */
    void setSelectedNode(String path);
  }

  /**
   * Show structure of the tree.
   *
   * @param nodes list of the project root nodes
   * @param nodeInterceptor interceptor for filtering nodes
   */
  void setStructure(List<Node> nodes, ClasspathNodeInterceptor nodeInterceptor);

  /** Show dialog. */
  void show();
}
