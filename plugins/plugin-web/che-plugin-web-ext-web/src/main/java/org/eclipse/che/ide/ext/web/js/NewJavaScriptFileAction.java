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
package org.eclipse.che.ide.ext.web.js;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.eclipse.che.ide.CoreLocalizationConstant;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.dialogs.DialogFactory;
import org.eclipse.che.ide.api.editor.EditorAgent;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.ext.web.WebLocalizationConstant;
import org.eclipse.che.ide.newresource.AbstractNewResourceAction;

/**
 * Action to create new JavaScript file.
 *
 * @author Artem Zatsarynnyi
 * @author Vlad Zhukovskyi
 */
@Singleton
public class NewJavaScriptFileAction extends AbstractNewResourceAction {

  @Inject
  public NewJavaScriptFileAction(
      WebLocalizationConstant localizationConstant,
      DialogFactory dialogFactory,
      CoreLocalizationConstant coreLocalizationConstant,
      EventBus eventBus,
      AppContext appContext,
      NotificationManager notificationManager,
      Provider<EditorAgent> editorAgentProvider) {
    super(
        localizationConstant.newJavaScriptFileActionTitle(),
        localizationConstant.newJavaScriptFileActionDescription(),
        null,
        dialogFactory,
        coreLocalizationConstant,
        eventBus,
        appContext,
        notificationManager,
        editorAgentProvider);
  }

  @Override
  protected String getExtension() {
    return "js";
  }
}
