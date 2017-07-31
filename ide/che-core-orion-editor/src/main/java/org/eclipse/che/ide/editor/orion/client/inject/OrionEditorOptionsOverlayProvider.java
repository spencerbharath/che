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
package org.eclipse.che.ide.editor.orion.client.inject;

import com.google.inject.Provider;
import org.eclipse.che.ide.editor.orion.client.jso.OrionEditorOptionsOverlay;

/** @author Alexander Andrienko */
public class OrionEditorOptionsOverlayProvider implements Provider<OrionEditorOptionsOverlay> {
  @Override
  public OrionEditorOptionsOverlay get() {
    return OrionEditorOptionsOverlay.createObject().cast();
  }
}
