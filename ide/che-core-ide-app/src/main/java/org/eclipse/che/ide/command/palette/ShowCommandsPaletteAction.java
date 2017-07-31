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
package org.eclipse.che.ide.command.palette;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.che.ide.api.action.Action;
import org.eclipse.che.ide.api.action.ActionEvent;

/**
 * Action for opening Commands Palette.
 *
 * @author Artem Zatsarynnyi
 */
@Singleton
public class ShowCommandsPaletteAction extends Action {

  private final CommandsPalettePresenter presenter;

  @Inject
  public ShowCommandsPaletteAction(PaletteMessages messages, CommandsPalettePresenter presenter) {
    super(messages.actionShowPaletteTitle(), messages.actionShowPaletteDescription(), null, null);

    this.presenter = presenter;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    presenter.showDialog();
  }
}
