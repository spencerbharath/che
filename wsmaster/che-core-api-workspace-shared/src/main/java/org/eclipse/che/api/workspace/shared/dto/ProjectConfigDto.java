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
import static org.eclipse.che.api.core.factory.FactoryParameter.Obligation.OPTIONAL;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import org.eclipse.che.api.core.factory.FactoryParameter;
import org.eclipse.che.api.core.model.project.ProjectConfig;
import org.eclipse.che.api.core.rest.shared.dto.Link;
import org.eclipse.che.dto.shared.DTO;

/** @author Alexander Garagatyi */
@DTO
public interface ProjectConfigDto extends ProjectConfig {
  @Override
  @FactoryParameter(obligation = MANDATORY)
  String getName();

  void setName(String name);

  ProjectConfigDto withName(String name);

  @Override
  @FactoryParameter(obligation = MANDATORY)
  String getPath();

  void setPath(String path);

  ProjectConfigDto withPath(String path);

  @Override
  @FactoryParameter(obligation = OPTIONAL)
  String getDescription();

  void setDescription(String description);

  ProjectConfigDto withDescription(String description);

  @Override
  @FactoryParameter(obligation = MANDATORY)
  String getType();

  void setType(String type);

  ProjectConfigDto withType(String type);

  @Override
  @FactoryParameter(obligation = OPTIONAL)
  List<String> getMixins();

  void setMixins(List<String> mixins);

  ProjectConfigDto withMixins(List<String> mixins);

  @Override
  @FactoryParameter(obligation = OPTIONAL)
  Map<String, List<String>> getAttributes();

  void setAttributes(Map<String, List<String>> attributes);

  ProjectConfigDto withAttributes(Map<String, List<String>> attributes);

  @Override
  @FactoryParameter(obligation = OPTIONAL)
  SourceStorageDto getSource();

  void setSource(SourceStorageDto source);

  ProjectConfigDto withSource(SourceStorageDto source);

  @FactoryParameter(obligation = OPTIONAL)
  List<Link> getLinks();

  void setLinks(List<Link> links);

  ProjectConfigDto withLinks(List<Link> links);

  /**
   * Provides information about project errors. If project doesn't have any error this field is
   * empty.
   */
  @ApiModelProperty(
    value =
        "Optional information about project errors. If project doesn't have any error this field is empty"
  )
  List<ProjectProblemDto> getProblems();

  /** @see #getProblems */
  void setProblems(List<ProjectProblemDto> problems);

  ProjectConfigDto withProblems(List<ProjectProblemDto> problems);
}
