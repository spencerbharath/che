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
package org.eclipse.che.api.vfs;

/**
 * Filter for virtual files.
 *
 * @author andrew00x
 * @deprecated VFS components are now considered deprecated and will be replaced by standard JDK
 *     routines.
 */
@Deprecated
public interface VirtualFileFilter {
  /** Tests whether specified file should be included in result. */
  boolean accept(VirtualFile file);

  VirtualFileFilter ACCEPT_ALL = file -> true;
}
