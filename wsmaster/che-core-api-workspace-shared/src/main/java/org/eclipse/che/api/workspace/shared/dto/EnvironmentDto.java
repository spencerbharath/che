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
package org.eclipse.che.api.workspace.shared.dto;

import static org.eclipse.che.api.core.factory.FactoryParameter.Obligation.MANDATORY;

import java.util.Map;
import org.eclipse.che.api.core.factory.FactoryParameter;
import org.eclipse.che.api.core.model.workspace.Environment;
import org.eclipse.che.dto.shared.DTO;

/** @author Alexander Garagatyi */
@DTO
public interface EnvironmentDto extends Environment {

  @Override
  @FactoryParameter(obligation = MANDATORY)
  EnvironmentRecipeDto getRecipe();

  void setRecipe(EnvironmentRecipeDto recipe);

  EnvironmentDto withRecipe(EnvironmentRecipeDto recipe);

  @Override
  @FactoryParameter(obligation = MANDATORY)
  Map<String, ExtendedMachineDto> getMachines();

  void setMachines(Map<String, ExtendedMachineDto> machines);

  EnvironmentDto withMachines(Map<String, ExtendedMachineDto> machines);
}
