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
package org.eclipse.che.api.core.model.factory;

/**
 * Defines factory button attributes.
 *
 * @author Anton Korneta
 */
public interface ButtonAttributes {

  /** Returns factory button color */
  String getColor();

  /** Returns factory button counter */
  Boolean getCounter();

  /** Returns factory button logo */
  String getLogo();

  /** Returns factory button style */
  String getStyle();
}
