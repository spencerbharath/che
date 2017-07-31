/**
 * ***************************************************************************** Copyright (c) 2016
 * Rogue Wave Software, Inc. All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Rogue Wave Software, Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.zdb.server.exceptions;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import org.eclipse.che.api.debugger.server.exceptions.DebuggerException;

/**
 * Simple timeout exception.
 *
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("serial")
public class ZendDbgTimeoutException extends DebuggerException {

  public ZendDbgTimeoutException(int timeout, TimeUnit unit) {
    super(MessageFormat.format("Response timeout ({0} {1}) occurred.", timeout, unit.name()));
  }
}
