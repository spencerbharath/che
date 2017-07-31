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
package org.eclipse.che.security.oauth;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.che.api.auth.shared.dto.OAuthToken;
import org.eclipse.che.api.core.BadRequestException;
import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.ForbiddenException;
import org.eclipse.che.api.core.NotFoundException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.core.UnauthorizedException;
import org.eclipse.che.api.core.rest.HttpJsonRequestFactory;
import org.eclipse.che.api.core.rest.shared.dto.Link;
import org.eclipse.che.dto.server.DtoFactory;
import org.eclipse.che.security.oauth.shared.OAuthTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allow get token from OAuth service over http.
 *
 * @author Max Shaposhnik
 * @author Sergii Kabashniuk
 */
public class RemoteOAuthTokenProvider implements OAuthTokenProvider {
  private static final Logger LOG = LoggerFactory.getLogger(RemoteOAuthTokenProvider.class);

  private final String apiEndpoint;

  private final HttpJsonRequestFactory httpJsonRequestFactory;

  @Inject
  public RemoteOAuthTokenProvider(
      @Named("che.api") String apiEndpoint, HttpJsonRequestFactory httpJsonRequestFactory) {
    this.apiEndpoint = apiEndpoint;
    this.httpJsonRequestFactory = httpJsonRequestFactory;
  }

  /** {@inheritDoc} */
  @Override
  public OAuthToken getToken(String oauthProviderName, String userId) throws IOException {
    if (userId.isEmpty()) {
      return null;
    }
    try {
      UriBuilder ub =
          UriBuilder.fromUri(apiEndpoint)
              .path("/oauth/token")
              .queryParam("oauth_provider", oauthProviderName);
      Link getTokenLink =
          DtoFactory.newDto(Link.class).withHref(ub.build().toString()).withMethod("GET");
      return httpJsonRequestFactory.fromLink(getTokenLink).request().asDto(OAuthToken.class);
    } catch (NotFoundException ne) {
      LOG.warn("Token not found for user {}", userId);
      return null;
    } catch (ServerException
        | UnauthorizedException
        | ForbiddenException
        | ConflictException
        | BadRequestException e) {
      LOG.warn("Exception on token retrieval, message : {}", e.getLocalizedMessage());
      return null;
    }
  }
}
