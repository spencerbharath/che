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
package org.eclipse.che.ide.editor.orion.client.signature;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple wrapper to create GWT Widget from DOM Elemen
 *
 * @author Evgen Vidolob
 */
public class ElementWidget extends Widget {

  public ElementWidget(Element element) {
    setElement(element);
  }
}
