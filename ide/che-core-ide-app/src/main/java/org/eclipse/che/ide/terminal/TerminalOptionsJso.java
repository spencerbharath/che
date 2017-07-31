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
package org.eclipse.che.ide.terminal;

import org.eclipse.che.ide.collections.Jso;

/** @author Evgen Vidolob */
class TerminalOptionsJso extends Jso {
  protected TerminalOptionsJso() {}

  public static native TerminalOptionsJso createDefault() /*-{
        return {
            cols: 80,
            rows: 24,
            screenKeys: true,
            focusOnOpen: false
        }
    }-*/;

  public final native TerminalOptionsJso withFocusOnOpen(boolean focusOnOpen) /*-{
        this.focusOnOpen = focusOnOpen;
    }-*/;
}
