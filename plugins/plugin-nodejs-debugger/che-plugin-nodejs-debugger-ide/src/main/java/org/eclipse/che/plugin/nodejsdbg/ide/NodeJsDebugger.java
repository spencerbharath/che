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
package org.eclipse.che.plugin.nodejsdbg.ide;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.eclipse.che.api.core.jsonrpc.commons.RequestHandlerConfigurator;
import org.eclipse.che.api.core.jsonrpc.commons.RequestHandlerManager;
import org.eclipse.che.api.core.jsonrpc.commons.RequestTransmitter;
import org.eclipse.che.api.debug.shared.model.Location;
import org.eclipse.che.ide.api.debug.BreakpointManager;
import org.eclipse.che.ide.api.debug.DebuggerServiceClient;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.api.resources.VirtualFile;
import org.eclipse.che.ide.debug.DebuggerDescriptor;
import org.eclipse.che.ide.debug.DebuggerManager;
import org.eclipse.che.ide.dto.DtoFactory;
import org.eclipse.che.ide.util.storage.LocalStorageProvider;
import org.eclipse.che.plugin.debugger.ide.debug.AbstractDebugger;
import org.eclipse.che.plugin.debugger.ide.debug.BasicActiveFileHandler;

/**
 * The NodeJs Debugger Client.
 *
 * @author Anatoliy Bazko
 */
public class NodeJsDebugger extends AbstractDebugger {

  public static final String ID = "nodejsdbg";

  @Inject
  public NodeJsDebugger(
      DebuggerServiceClient service,
      RequestTransmitter transmitter,
      RequestHandlerConfigurator configurator,
      DtoFactory dtoFactory,
      LocalStorageProvider localStorageProvider,
      EventBus eventBus,
      BasicActiveFileHandler activeFileHandler,
      DebuggerManager debuggerManager,
      NotificationManager notificationManager,
      BreakpointManager breakpointManager,
      RequestHandlerManager requestHandlerManager) {

    super(
        service,
        transmitter,
        configurator,
        dtoFactory,
        localStorageProvider,
        eventBus,
        activeFileHandler,
        debuggerManager,
        notificationManager,
        breakpointManager,
        ID,
        requestHandlerManager);
  }

  @Override
  protected String fqnToPath(@NotNull Location location) {
    return location.getResourcePath() == null ? location.getTarget() : location.getResourcePath();
  }

  @Override
  protected String pathToFqn(VirtualFile file) {
    return file.getLocation().toString();
  }

  @Override
  protected DebuggerDescriptor toDescriptor(Map<String, String> connectionProperties) {
    StringBuilder sb = new StringBuilder();

    for (String propName : connectionProperties.keySet()) {
      try {
        ConnectionProperties prop = ConnectionProperties.valueOf(propName.toUpperCase());
        String connectionInfo = prop.getConnectionInfo(connectionProperties.get(propName));
        if (!connectionInfo.isEmpty()) {
          if (sb.length() > 0) {
            sb.append(',');
          }
          sb.append(connectionInfo);
        }
      } catch (IllegalArgumentException ignored) {
        // unrecognized connection property
      }
    }

    return new DebuggerDescriptor("", "{ " + sb.toString() + " }");
  }

  public enum ConnectionProperties {
    SCRIPT {
      @Override
      public String getConnectionInfo(String value) {
        return value;
      }
    };

    public abstract String getConnectionInfo(String value);
  }
}
