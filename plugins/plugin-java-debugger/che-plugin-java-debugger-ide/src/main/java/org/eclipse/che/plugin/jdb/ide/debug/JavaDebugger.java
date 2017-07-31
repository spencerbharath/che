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
package org.eclipse.che.plugin.jdb.ide.debug;

import static org.eclipse.che.plugin.jdb.ide.debug.JavaDebugger.ConnectionProperties.HOST;
import static org.eclipse.che.plugin.jdb.ide.debug.JavaDebugger.ConnectionProperties.PORT;

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
import org.eclipse.che.ide.api.filetypes.FileTypeRegistry;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.api.resources.VirtualFile;
import org.eclipse.che.ide.debug.DebuggerDescriptor;
import org.eclipse.che.ide.debug.DebuggerManager;
import org.eclipse.che.ide.dto.DtoFactory;
import org.eclipse.che.ide.util.storage.LocalStorageProvider;
import org.eclipse.che.plugin.debugger.ide.debug.AbstractDebugger;
import org.eclipse.che.plugin.debugger.ide.fqn.FqnResolver;
import org.eclipse.che.plugin.debugger.ide.fqn.FqnResolverFactory;

/**
 * The java debugger.
 *
 * @author Anatoliy Bazko
 */
public class JavaDebugger extends AbstractDebugger {

  public static final String ID = "jdb";

  public final FqnResolverFactory fqnResolverFactory;
  public final FileTypeRegistry fileTypeRegistry;

  @Inject
  public JavaDebugger(
      DebuggerServiceClient service,
      RequestTransmitter transmitter,
      DtoFactory dtoFactory,
      RequestHandlerConfigurator configurator,
      LocalStorageProvider localStorageProvider,
      EventBus eventBus,
      FqnResolverFactory fqnResolverFactory,
      JavaDebuggerFileHandler javaDebuggerFileHandler,
      DebuggerManager debuggerManager,
      NotificationManager notificationManager,
      FileTypeRegistry fileTypeRegistry,
      BreakpointManager breakpointManager,
      RequestHandlerManager requestHandlerManager) {
    super(
        service,
        transmitter,
        configurator,
        dtoFactory,
        localStorageProvider,
        eventBus,
        javaDebuggerFileHandler,
        debuggerManager,
        notificationManager,
        breakpointManager,
        ID,
        requestHandlerManager);
    this.fqnResolverFactory = fqnResolverFactory;
    this.fileTypeRegistry = fileTypeRegistry;
  }

  @Override
  protected String fqnToPath(@NotNull Location location) {
    String resourcePath = location.getResourcePath();
    return resourcePath != null ? resourcePath : location.getTarget();
  }

  @Override
  protected String pathToFqn(VirtualFile file) {
    String fileExtension = fileTypeRegistry.getFileTypeByFile(file).getExtension();

    FqnResolver resolver = fqnResolverFactory.getResolver(fileExtension);
    if (resolver != null) {
      return resolver.resolveFqn(file);
    }

    return null;
  }

  @Override
  protected DebuggerDescriptor toDescriptor(Map<String, String> connectionProperties) {
    String address =
        connectionProperties.get(HOST.toString()) + ":" + connectionProperties.get(PORT.toString());
    return new DebuggerDescriptor("", address);
  }

  public enum ConnectionProperties {
    HOST,
    PORT
  }
}
