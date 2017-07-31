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

import org.eclipse.che.api.debug.shared.model.action.Action;
import org.eclipse.che.api.debug.shared.model.action.ResumeAction;
import org.eclipse.che.dto.shared.DTO;

/** @author Anatoliy Bazko */
@DTO
public interface ResumeActionDto extends ActionDto, ResumeAction {
  Action.TYPE getType();

  void setType(Action.TYPE type);

  ResumeActionDto withType(Action.TYPE type);
}
