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
package org.eclipse.che.api.debug.shared.dto.event;

import org.eclipse.che.api.debug.shared.dto.LocationDto;
import org.eclipse.che.dto.shared.DTO;

/** @author andrew00x */
@DTO
public interface SuspendEventDto extends DebuggerEventDto {
  TYPE getType();

  void setType(TYPE type);

  SuspendEventDto withType(TYPE type);

  LocationDto getLocation();

  void setLocation(LocationDto location);

  SuspendEventDto withLocation(LocationDto location);
}
