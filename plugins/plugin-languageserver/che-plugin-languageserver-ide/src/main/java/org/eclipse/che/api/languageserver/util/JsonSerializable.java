/**
 * ***************************************************************************** Copyright (c) 2017
 * Red Hat. All rights reserved. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Red Hat - Initial Contribution
 * *****************************************************************************
 */
package org.eclipse.che.api.languageserver.util;

import com.google.gwt.json.client.JSONValue;

/**
 * Extend JsonSerializable with a conversion to a json value. This is used in the generated DTO
 * conversions.
 *
 * @author Thomas Mäder
 */
public interface JsonSerializable extends org.eclipse.che.ide.dto.JsonSerializable {
  JSONValue toJsonElement();
}
