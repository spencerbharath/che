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
package org.eclipse.che.ide.api.project;

import org.eclipse.che.api.project.shared.dto.ProjectImporterData;
import org.eclipse.che.ide.api.machine.DevMachine;
import org.eclipse.che.ide.rest.AsyncRequestCallback;

/** @author Vitaly Parfonov */
public interface ProjectImportersServiceClient {

  void getProjectImporters(
      DevMachine devMachine, AsyncRequestCallback<ProjectImporterData> callback);
}
