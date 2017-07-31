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
package org.eclipse.che.plugin.languageserver.ide.service;

import static org.eclipse.che.ide.MimeType.APPLICATION_JSON;
import static org.eclipse.che.ide.rest.HTTPHeader.ACCEPT;
import static org.eclipse.che.ide.rest.HTTPHeader.CONTENT_TYPE;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.rest.AsyncRequestFactory;
import org.eclipse.che.ide.rest.DtoUnmarshallerFactory;
import org.eclipse.che.ide.rest.Unmarshallable;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbolParams;

/** @author Evgen Vidolob */
@Singleton
public class WorkspaceServiceClient {
  private final DtoUnmarshallerFactory unmarshallerFactory;
  private final AppContext appContext;
  private final AsyncRequestFactory asyncRequestFactory;

  @Inject
  public WorkspaceServiceClient(
      final DtoUnmarshallerFactory unmarshallerFactory,
      final AppContext appContext,
      final AsyncRequestFactory asyncRequestFactory) {
    this.unmarshallerFactory = unmarshallerFactory;
    this.appContext = appContext;
    this.asyncRequestFactory = asyncRequestFactory;
  }

  /**
   * GWT client implementation of {@link
   * io.typefox.lsapi.WorkspaceService#symbol(io.typefox.lsapi.WorkspaceSymbolParams)}
   *
   * @param params
   * @return
   */
  public Promise<List<SymbolInformation>> symbol(WorkspaceSymbolParams params) {
    String requestUrl =
        appContext.getDevMachine().getWsAgentBaseUrl() + "/languageserver/workspace/symbol";
    Unmarshallable<List<SymbolInformation>> unmarshaller =
        unmarshallerFactory.newListUnmarshaller(SymbolInformation.class);
    return asyncRequestFactory
        .createPostRequest(requestUrl, params)
        .header(ACCEPT, APPLICATION_JSON)
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .send(unmarshaller);
  }
}
