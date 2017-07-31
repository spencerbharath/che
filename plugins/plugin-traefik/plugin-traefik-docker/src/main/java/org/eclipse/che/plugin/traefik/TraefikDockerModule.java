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
package org.eclipse.che.plugin.traefik;

import static com.google.inject.matcher.Matchers.subclassesOf;
import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getenv;
import static org.eclipse.che.inject.Matchers.names;

import com.google.inject.AbstractModule;
import org.eclipse.che.plugin.docker.client.DockerConnector;

/**
 * The Module for Traefik components.
 *
 * @author Florent Benoit
 */
public class TraefikDockerModule extends AbstractModule {

  /** Configure the traefik components */
  @Override
  protected void configure() {

    // add logic only if plug-in is enabled.
    if (parseBoolean(getenv("CHE_PLUGIN_TRAEFIK_ENABLED"))) {
      // add an interceptor to intercept createContainer calls and then get the final labels
      final TraefikCreateContainerInterceptor traefikCreateContainerInterceptor =
          new TraefikCreateContainerInterceptor();
      requestInjection(traefikCreateContainerInterceptor);
      bindInterceptor(
          subclassesOf(DockerConnector.class),
          names("createContainer"),
          traefikCreateContainerInterceptor);
    }
  }
}
