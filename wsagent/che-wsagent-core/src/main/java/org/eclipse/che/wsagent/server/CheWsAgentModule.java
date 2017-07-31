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
package org.eclipse.che.wsagent.server;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.net.URI;
import org.eclipse.che.ApiEndpointAccessibilityChecker;
import org.eclipse.che.EventBusURLProvider;
import org.eclipse.che.UriApiEndpointProvider;
import org.eclipse.che.UserTokenProvider;
import org.eclipse.che.inject.DynaModule;

/**
 * Configuration of Che ws agent core part that can be different in different assembly.
 *
 * @author Sergii Kabashniuk
 */
@DynaModule
public class CheWsAgentModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(URI.class).annotatedWith(Names.named("che.api")).toProvider(UriApiEndpointProvider.class);
    bind(String.class).annotatedWith(Names.named("user.token")).toProvider(UserTokenProvider.class);

    bind(String.class)
        .annotatedWith(Names.named("event.bus.url"))
        .toProvider(EventBusURLProvider.class);
    bind(ApiEndpointAccessibilityChecker.class);
    bind(WsAgentAnalyticsAddresser.class);

    bind(String.class)
        .annotatedWith(Names.named("wsagent.endpoint"))
        .toProvider(WsAgentURLProvider.class);
  }
}
