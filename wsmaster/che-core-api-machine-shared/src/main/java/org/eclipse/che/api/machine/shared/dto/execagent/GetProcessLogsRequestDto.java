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
package org.eclipse.che.api.machine.shared.dto.execagent;

import org.eclipse.che.api.machine.shared.dto.execagent.event.DtoWithPid;
import org.eclipse.che.dto.shared.DTO;

@DTO
public interface GetProcessLogsRequestDto extends DtoWithPid {
  GetProcessLogsRequestDto withPid(int pid);

  String getFrom();

  GetProcessLogsRequestDto withFrom(String from);

  String getTill();

  GetProcessLogsRequestDto withTill(String till);

  String getFormat();

  GetProcessLogsRequestDto withFormat(String format);

  int getLimit();

  GetProcessLogsRequestDto withLimit(int limit);

  int getSkip();

  GetProcessLogsRequestDto withSkip(int limit);
}
