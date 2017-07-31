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
package org.eclipse.che.plugin.nodejsdbg.ide;

import com.google.gwt.resources.client.ClientBundle;
import org.vectomatic.dom.svg.ui.SVGResource;

/** @author Anatolii Bazko */
public interface NodeJsDebuggerResources extends ClientBundle {

  /** Returns the icon for NodeJs debugger configurations. */
  @Source("configuration/nodejs-debugger-configuration-type.svg")
  SVGResource nodeJsDebugConfigurationType();
}
