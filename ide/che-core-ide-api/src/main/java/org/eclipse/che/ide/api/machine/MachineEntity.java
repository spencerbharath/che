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
package org.eclipse.che.ide.api.machine;

import java.util.List;
import java.util.Map;
import org.eclipse.che.api.core.model.machine.Machine;
import org.eclipse.che.api.core.model.machine.Server;
import org.eclipse.che.api.core.rest.shared.dto.Link;

/**
 * Defines machine entity on client side.
 *
 * @author Roman Nikitenko
 */
public interface MachineEntity extends Machine {

  /**
   * Returns {@code true} when the machine entity is development machine and {@code false} otherwise
   */
  boolean isDev();

  /** Machine type (i.e. "docker"). */
  String getType();

  /** Returns current machine's display name */
  String getDisplayName();

  /** Returns machine specific properties. */
  Map<String, String> getProperties();

  /**
   * Returns url to connects to special WebSocket which allows get information from terminal on
   * server side.
   */
  String getTerminalUrl();

  /** Returns url to connects to special WebSocket which allows execute command on given machine */
  String getExecAgentUrl();

  /** Returns mapping of exposed ports to {@link Server}. */
  Map<String, ? extends Server> getServers();

  /** Returns {@link Server} by reference or null if it not exists. */
  Server getServer(String ref);

  List<Link> getMachineLinks();

  Link getMachineLink(String ref);

  Map<String, String> getEnvVariables();

  /** Returns {@link Machine descriptor} */
  Machine getDescriptor();
}
