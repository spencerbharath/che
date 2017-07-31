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
package org.eclipse.che.ide.editor.orion.client;

import com.google.inject.Singleton;
import javax.inject.Inject;
import org.eclipse.che.ide.api.extension.Extension;

@Extension(title = "Orion Editor", version = "1.1.0")
@Singleton
public class OrionEditorExtension {

  @Inject
  public OrionEditorExtension() {
    KeyMode.init();
  }
}
