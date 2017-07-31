/**
 * ***************************************************************************** Copyright (c)
 * 2012-2017 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.testing.ide;

import org.eclipse.che.ide.api.action.DefaultActionGroup;

/**
 * Interface for defining test framework IDE actions for the test runner. All test framework
 * implementations should implement this interface in order to appear testing actions in menus
 *
 * @author Mirage Abeysekara
 */
@Deprecated
public interface TestAction {

  /**
   * This method get called when the extension is loading and adding the menu items for the main
   * menu.
   *
   * @param testMainMenu Main menu item for test actions
   */
  void addMainMenuItems(DefaultActionGroup testMainMenu);

  /**
   * This method get called when the extension is loading and adding the menu items for the project
   * explorer context menu.
   *
   * @param testContextMenu Context menu item for test actions
   */
  void addContextMenuItems(DefaultActionGroup testContextMenu);
}
