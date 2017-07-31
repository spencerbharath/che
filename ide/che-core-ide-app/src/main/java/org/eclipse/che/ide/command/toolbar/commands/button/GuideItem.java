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
package org.eclipse.che.ide.command.toolbar.commands.button;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.eclipse.che.ide.api.command.CommandGoal;
import org.eclipse.che.ide.command.toolbar.ToolbarMessages;
import org.eclipse.che.ide.ui.menubutton.MenuItem;

/**
 * A {@link MenuItem} represents a hint which guides the user into the flow of creating a command.
 */
public class GuideItem implements MenuItem {

  private final ToolbarMessages messages;
  private final CommandGoal goal;

  @Inject
  public GuideItem(ToolbarMessages messages, @Assisted CommandGoal goal) {
    this.messages = messages;
    this.goal = goal;
  }

  @Override
  public String getName() {
    return messages.guideItemLabel(goal.getId());
  }
}
