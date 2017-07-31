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
package org.eclipse.che.ide.ui.smartTree.compare;

import java.util.Comparator;
import org.eclipse.che.ide.api.data.tree.Node;

/**
 * Comparator which compare nodes by their name.
 *
 * @author Vlad Zhukovskyi
 */
public class NameComparator implements Comparator<Node> {

  /** {@inheritDoc} */
  @Override
  public int compare(Node o1, Node o2) {
    return o1.getName().compareTo(o2.getName());
  }
}
