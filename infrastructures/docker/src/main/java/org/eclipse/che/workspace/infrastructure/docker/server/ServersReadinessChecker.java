/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.workspace.infrastructure.docker.server;

import com.google.common.collect.ImmutableMap;

import org.eclipse.che.api.workspace.server.model.impl.ServerImpl;
import org.eclipse.che.api.workspace.server.spi.InfrastructureException;
import org.eclipse.che.api.workspace.server.spi.InternalInfrastructureException;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * Checks readiness of servers of a machine.
 *
 * @author Alexander Garagatyi
 */
public class ServersReadinessChecker {
    // workaround to set correct paths for servers readiness checks
    // TODO replace with checks set in server config
    private final static Map<String, String> LIVENESS_CHECKS_PATHS = ImmutableMap.of("wsagent/http", "/api/",
                                                                                     "exec-agent/http", "/process",
                                                                                     "terminal", "/");
    private final String                  machineName;
    private final Map<String, ServerImpl> servers;
    private final ServerCheckerFactory    serverCheckerFactory;

    private Timer             timer;
    private long              resultTimeoutSeconds;
    private CompletableFuture result;

    /**
     * Creates instance of this class.
     *
     * @param machineName
     *         name of machine whose servers will be checked by this method
     * @param servers
     *         map of servers in a machine
     */
    public ServersReadinessChecker(String machineName,
                                   Map<String, ServerImpl> servers,
                                   ServerCheckerFactory serverCheckerFactory) {
        this.machineName = machineName;
        this.servers = servers;
        this.serverCheckerFactory = serverCheckerFactory;
        this.timer = new Timer("ServerReadinessChecker", true);
    }

    /**
     * Asynchronously starts checking readiness of servers of a machine.
     *
     * @param serverReadinessHandler
     *         consumer which will be called with server reference as the argument when server become available
     * @throws InternalInfrastructureException
     *         if check of a server failed due to an unexpected error
     * @throws InfrastructureException
     *         if check of a server failed due to an error
     */
    public void startAsync(Consumer<String> serverReadinessHandler) throws InfrastructureException {
        timer = new Timer("ServerReadinessChecker", true);
        List<ServerChecker> serverCheckers = getServerCheckers();
        // should be completed with an exception if a server considered unavailable
        CompletableFuture<Void> firstNonAvailable = new CompletableFuture<>();
        CompletableFuture[] checkTasks = serverCheckers
                .stream()
                .map(ServerChecker::getReportCompFuture)
                .map(compFut -> compFut.thenAccept(serverReadinessHandler)
                                       .exceptionally(e -> {
                                           // cleanup checkers tasks
                                           timer.cancel();
                                           firstNonAvailable.completeExceptionally(e);
                                           return null;
                                       }))
                .toArray(CompletableFuture[]::new);
        resultTimeoutSeconds = checkTasks.length * 180;
        // should complete when all servers checks reported availability
        CompletableFuture<Void> allAvailable = CompletableFuture.allOf(checkTasks);
        // should complete when all servers are available or any server is unavailable
        result = CompletableFuture.anyOf(allAvailable, firstNonAvailable);
        for (ServerChecker serverChecker : serverCheckers) {
            serverChecker.start();
        }
    }

    /**
     * Synchronously checks whether servers are available,
     * throws {@link InfrastructureException} if any is not.
     */
    public void checkOnce(Consumer<String> readyHandler) throws InfrastructureException {
        for (ServerChecker checker : getServerCheckers()) {
            checker.checkOnce(readyHandler);
        }
    }

    /**
     * Waits until servers are considered available or one of them is considered as unavailable.
     *
     * @throws InternalInfrastructureException
     *         if check of a server failed due to an unexpected error
     * @throws InfrastructureException
     *         if check of a server failed due to interruption
     * @throws InfrastructureException
     *         if check of a server failed because it reached timeout
     * @throws InfrastructureException
     *         if check of a server failed due to an error
     */
    public void await() throws InfrastructureException, InterruptedException {
        try {
            // TODO how much time should we check?
            result.get(resultTimeoutSeconds, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new InfrastructureException("Servers readiness check of machine " + machineName + " timed out");
        } catch (ExecutionException e) {
            try {
                throw e.getCause();
            } catch (InfrastructureException rethrow) {
                throw rethrow;
            } catch (Throwable thr) {
                throw new InternalInfrastructureException(
                        "Machine " + machineName + " servers readiness check failed. Error: " + thr.getMessage(), thr);
            }
        }
    }

    private List<ServerChecker> getServerCheckers() throws InfrastructureException {
        ArrayList<ServerChecker> checkers = new ArrayList<>(servers.size());
        for (Map.Entry<String, ServerImpl> serverEntry : servers.entrySet()) {
            // TODO replace with correct behaviour
            // workaround needed because we don't have server readiness check in the model
            if (LIVENESS_CHECKS_PATHS.containsKey(serverEntry.getKey())) {
                checkers.add(getChecker(serverEntry.getKey(),
                                        serverEntry.getValue()));
            }
        }
        return checkers;
    }

    private ServerChecker getChecker(String serverRef, ServerImpl server) throws InfrastructureException {
        // TODO replace with correct behaviour
        // workaround needed because we don't have server readiness check in the model
        String livenessCheckPath = LIVENESS_CHECKS_PATHS.get(serverRef);
        // Create server readiness endpoint URL
        URL url;
        try {
            url = UriBuilder.fromUri(server.getUrl().replaceFirst("^ws", "http"))
                            .replacePath(livenessCheckPath)
                            .build()
                            .toURL();
        } catch (MalformedURLException e) {
            throw new InternalInfrastructureException(
                    "Server " + serverRef + " URL is invalid. Error: " + e.getMessage(), e);
        }

        return serverCheckerFactory.httpChecker(url, machineName, serverRef, timer);
    }
}
