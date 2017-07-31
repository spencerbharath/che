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
package org.eclipse.che.workspace.infrastructure.docker;

import org.eclipse.che.api.core.model.workspace.config.ServerConfig;
import org.eclipse.che.api.core.model.workspace.runtime.RuntimeIdentity;
import org.eclipse.che.api.workspace.server.model.impl.ServerConfigImpl;
import org.eclipse.che.api.workspace.server.spi.RuntimeIdentityImpl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helps to convert docker infrastructure entities
 * to docker labels and vise-versa.
 *
 * @author Yevhenii Voevodin
 */
public final class Labels {

    public static final String LABEL_PREFIX          = "org.eclipse.che.";
    public static final String LABEL_WORKSPACE_ID    = LABEL_PREFIX + "workspace.id";
    public static final String LABEL_WORKSPACE_ENV   = LABEL_PREFIX + "workspace.env";
    public static final String LABEL_WORKSPACE_OWNER = LABEL_PREFIX + "workspace.owner";

    private static final String LABEL_MACHINE_NAME        = LABEL_PREFIX + "machine.name";
    private static final String SERVER_PORT_LABEL_FMT     = LABEL_PREFIX + "server.%s.port";
    private static final String SERVER_PROTOCOL_LABEL_FMT = LABEL_PREFIX + "server.%s.protocol";
    private static final String SERVER_PATH_LABEL_FMT     = LABEL_PREFIX + "server.%s.path";

    /** Pattern that matches server labels e.g. "org.eclipse.che.server.exec-agent.port". */
    private static final Pattern SERVER_LABEL_PATTERN = Pattern.compile("org\\.eclipse\\.che\\.server\\.(?<ref>[\\w-/]+)\\..+");

    /** Creates new label serializer. */
    public static Serializer newSerializer() { return new Serializer(); }

    /** Creates new label deserializer from given labels. */
    public static Deserializer newDeserializer(Map<String, String> labels) { return new Deserializer(labels); }

    /** Helps to serialize known entities to docker labels. */
    public static class Serializer {

        private final Map<String, String> labels = new LinkedHashMap<>();

        /**
         * Serializes machine name as docker container label.
         * Appends serialization result to this aggregate.
         *
         * @param name
         *         machine name
         * @return this serializer
         */
        public Serializer machineName(String name) {
            labels.put(LABEL_MACHINE_NAME, name);
            return this;
        }

        /**
         * Serializes runtime identity as docker container labels.
         * Appends serialization result to this aggregate.
         *
         * @param runtimeId
         *         the id of runtime
         * @return this serializer
         */
        public Serializer runtimeId(RuntimeIdentity runtimeId) {
            labels.put(LABEL_WORKSPACE_ID, runtimeId.getWorkspaceId());
            labels.put(LABEL_WORKSPACE_ENV, runtimeId.getEnvName());
            labels.put(LABEL_WORKSPACE_OWNER, runtimeId.getOwner());
            return this;
        }

        /**
         * Serializes server configuration as docker container labels.
         * Appends serialization result to this aggregate.
         *
         * @param ref
         *         server reference e.g. "exec-agent"
         * @param server
         *         server configuration
         * @return this serializer
         */
        public Serializer server(String ref, ServerConfig server) {
            labels.put(String.format(SERVER_PORT_LABEL_FMT, ref), server.getPort());
            labels.put(String.format(SERVER_PROTOCOL_LABEL_FMT, ref), server.getProtocol());
            labels.put(String.format(SERVER_PATH_LABEL_FMT, ref), server.getPath());
            return this;
        }

        /**
         * Serializer many servers as docker container labels.
         * Appends serialization result to this aggregate.
         *
         * @param servers
         *         ref -> server map
         * @return this serializer
         */
        public Serializer servers(Map<String, ? extends ServerConfig> servers) {
            servers.forEach(this::server);
            return this;
        }

        /** Returns docker container labels aggregated during serialization. */
        public Map<String, String> labels() { return labels; }
    }

    /** Helps to deserializer docker labels to known entities. */
    public static class Deserializer {

        private final Map<String, String> labels;

        public Deserializer(Map<String, String> labels) { this.labels = Objects.requireNonNull(labels); }

        /** Retrieves machine name from docker container labels and returns it. */
        public String machineName() {
            return labels.get(LABEL_MACHINE_NAME);
        }

        /** Retrieves runtime identity from docker labels and returns it. */
        public RuntimeIdentity runtimeId() {
            return new RuntimeIdentityImpl(labels.get(LABEL_WORKSPACE_ID),
                                           labels.get(LABEL_WORKSPACE_ENV),
                                           labels.get(LABEL_WORKSPACE_OWNER));
        }

        /** Retrieves server configuration from docker labels and returns (ref -> server config) map. */
        public Map<String, ServerConfig> servers() {
            Map<String, ServerConfig> servers = new HashMap<>();
            for (Map.Entry<String, String> entry : labels.entrySet()) {
                Matcher refMatcher = SERVER_LABEL_PATTERN.matcher(entry.getKey());
                if (refMatcher.matches()) {
                    String ref = refMatcher.group("ref");
                    if (!servers.containsKey(ref)) {
                        servers.put(ref, new ServerConfigImpl(labels.get(String.format(SERVER_PORT_LABEL_FMT, ref)),
                                                              labels.get(String.format(SERVER_PROTOCOL_LABEL_FMT, ref)),
                                                              labels.get(String.format(SERVER_PATH_LABEL_FMT, ref))));
                    }
                }
            }
            return servers;
        }
    }

    private Labels() {}
}
