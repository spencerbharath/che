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
package org.eclipse.che.ide.ext.help.client;

import static com.google.common.base.Strings.isNullOrEmpty;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.che.ide.api.ProductInfoDataProvider;
import org.eclipse.che.ide.api.action.Action;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.util.browser.BrowserUtils;

/**
 * Redirect to support window
 *
 * @author Oleksii Orel
 * @author Alexander Andrienko
 */
@Singleton
public class RedirectToSupportAction extends Action {
  private final ProductInfoDataProvider productInfoDataProvider;

  @Inject
  public RedirectToSupportAction(
      HelpExtensionLocalizationConstant locale,
      ProductInfoDataProvider productInfoDataProvider,
      AboutResources resources) {
    super(
        productInfoDataProvider.getSupportTitle(),
        locale.actionRedirectToSupportDescription(),
        null,
        resources.getSupport());
    this.productInfoDataProvider = productInfoDataProvider;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    BrowserUtils.openInNewTab(productInfoDataProvider.getSupportLink());
  }

  @Override
  public void update(ActionEvent event) {
    event.getPresentation().setVisible(!isNullOrEmpty(productInfoDataProvider.getSupportLink()));
  }
}
