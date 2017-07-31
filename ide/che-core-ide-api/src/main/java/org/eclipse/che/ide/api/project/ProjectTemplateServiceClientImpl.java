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

import static org.eclipse.che.ide.MimeType.APPLICATION_JSON;
import static org.eclipse.che.ide.rest.HTTPHeader.ACCEPT;

import com.google.inject.Inject;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.eclipse.che.api.project.templates.shared.dto.ProjectTemplateDescriptor;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.rest.AsyncRequestCallback;
import org.eclipse.che.ide.rest.AsyncRequestFactory;
import org.eclipse.che.ide.rest.AsyncRequestLoader;
import org.eclipse.che.ide.ui.loaders.request.LoaderFactory;

/**
 * The implementation for {@link ProjectTemplateServiceClient}.
 *
 * @author Artem Zatsarynnyi
 */
public class ProjectTemplateServiceClientImpl implements ProjectTemplateServiceClient {

  private final AsyncRequestFactory asyncRequestFactory;
  private final AsyncRequestLoader loader;

  private String baseUrl;

  @Inject
  protected ProjectTemplateServiceClientImpl(
      AppContext appContext, AsyncRequestFactory asyncRequestFactory, LoaderFactory loaderFactory) {
    this.asyncRequestFactory = asyncRequestFactory;
    this.loader = loaderFactory.newLoader();

    baseUrl = appContext.getMasterEndpoint() + "/project-template/";
  }

  @Override
  public void getProjectTemplates(
      @NotNull List<String> tags,
      @NotNull AsyncRequestCallback<List<ProjectTemplateDescriptor>> callback) {
    final StringBuilder tagsParam = new StringBuilder();
    for (String tag : tags) {
      tagsParam.append("tag=").append(tag).append("&");
    }
    if (tagsParam.length() > 0) {
      tagsParam.deleteCharAt(tagsParam.length() - 1); // delete last ampersand
    }

    final String requestUrl = baseUrl + '?' + tagsParam.toString();
    asyncRequestFactory
        .createGetRequest(requestUrl)
        .header(ACCEPT, APPLICATION_JSON)
        .loader(loader)
        .send(callback);
  }

  @Override
  public void getProjectTemplates(
      @NotNull AsyncRequestCallback<List<ProjectTemplateDescriptor>> callback) {
    asyncRequestFactory
        .createGetRequest(baseUrl + "all")
        .header(ACCEPT, APPLICATION_JSON)
        .loader(loader)
        .send(callback);
  }
}
