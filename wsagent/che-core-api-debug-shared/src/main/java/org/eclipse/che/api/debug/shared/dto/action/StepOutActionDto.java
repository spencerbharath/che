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
package org.eclipse.che.api.debug.shared.dto.action;

import org.eclipse.che.api.debug.shared.model.action.StepOutAction;
import org.eclipse.che.dto.shared.DTO;

/** @author Anatoliy Bazko */
@DTO
public interface StepOutActionDto extends ActionDto, StepOutAction {
  TYPE getType();

  void setType(TYPE type);

  StepOutActionDto withType(TYPE type);
}
