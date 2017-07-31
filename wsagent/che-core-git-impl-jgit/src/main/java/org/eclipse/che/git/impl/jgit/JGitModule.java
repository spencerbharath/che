/*
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *   SAP           - implementation
 */
package org.eclipse.che.git.impl.jgit;

import com.google.inject.AbstractModule;
import org.eclipse.che.api.git.GitConnectionFactory;

/**
 * Guice module to install jgit implementation of git components
 *
 * @author Sergii Kabashnyuk
 */
public class JGitModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(GitConnectionFactory.class).to(JGitConnectionFactory.class);
  }
}
