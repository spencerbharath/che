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

import org.eclipse.che.api.promises.client.js.JsPromise;
import org.eclipse.che.ide.editor.orion.client.jso.OrionOccurrenceContextOverlay;
import org.eclipse.che.ide.editor.orion.client.jso.OrionOccurrenceOverlay;

/** @author Xavier Coulon, Red Hat */
public interface OrionOccurrencesHandler {
  JsPromise<OrionOccurrenceOverlay[]> computeOccurrences(OrionOccurrenceContextOverlay context);
}
