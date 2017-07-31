/**
 * ***************************************************************************** Copyright (c)
 * 2012-2015 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.core.internal.resources;

public abstract class ModelObject implements Cloneable {
  protected String name;

  public ModelObject() {
    super();
  }

  public ModelObject(String name) {
    setName(name);
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return null; // won't happen
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String value) {
    name = value;
  }
}
