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
package org.eclipse.che.api.workspace.shared;

/**
 * Constants for Workspace API
 *
 * @author Yevhenii Voevodin
 */
public final class Constants {

    public static final String WORKSPACE_STOPPED_BY           = "stopped_by";
    public static final String AUTO_CREATE_SNAPSHOT           = "auto_snapshot";
    public static final String AUTO_RESTORE_FROM_SNAPSHOT     = "auto_restore";
    public static final String LINK_REL_GET_WORKSPACES        = "get workspaces";
    public static final String LINK_REL_GET_BY_NAMESPACE      = "get by namespace";
    public static final String LINK_REL_CREATE_WORKSPACE      = "create workspace";
    public static final String LINK_REL_REMOVE_WORKSPACE      = "remove workspace";
    public static final String LINK_REL_START_WORKSPACE       = "start workspace";
    public static final String LINK_REL_GET_RUNTIME_WORKSPACE = "get runtime workspace";
    public static final String LINK_REL_STOP_WORKSPACE        = "stop workspace";
    public static final String GET_ALL_USER_WORKSPACES        = "get all user workspaces";
    public static final String LINK_REL_GET_SNAPSHOT          = "get workspace snapshot";
    public static final String LINK_REL_SELF                  = "self link";
    public static final String LINK_REL_IDE_URL               = "ide url";
    public static final String LIN_REL_GET_WORKSPACE          = "get workspace";

    public static final String LINK_REL_CREATE_STACK          = "create stack";
    public static final String LINK_REL_UPDATE_STACK          = "update stack";
    public static final String LINK_REL_REMOVE_STACK          = "remove stack";
    public static final String LINK_REL_GET_STACK_BY_ID       = "get stack by id";
    public static final String LINK_REL_GET_STACKS_BY_CREATOR = "get stacks by creator";
    public static final String LINK_REL_SEARCH_STACKS         = "search stacks";

    public static final String LINK_REL_REMOVE_RECIPE              = "remove recipe";
    public static final String LINK_REL_GET_RECIPE_SCRIPT          = "get recipe script";
    public static final String LINK_REL_CREATE_RECIPE              = "create recipe";
    public static final String LINK_REL_SEARCH_RECIPES             = "search recipes";
    public static final String LINK_REL_UPDATE_RECIPE              = "update recipe";

    public static final String LINK_REL_GET_ICON    = "get icon link";
    public static final String LINK_REL_UPLOAD_ICON = "upload icon link";
    public static final String LINK_REL_DELETE_ICON = "delete icon link";

    public static final String LINK_REL_GET_WORKSPACE_EVENTS_CHANNEL = "get workspace events channel";

    public static final String WS_AGENT_PROCESS_NAME = "CheWsAgent";

    public static final String CHE_WORKSPACE_AUTO_SNAPSHOT           = "che.workspace.auto_snapshot";
    public static final String CHE_WORKSPACE_AUTO_RESTORE            = "che.workspace.auto_restore";
    public static final String CHE_WORKSPACE_AUTO_START              = "che.workspace.auto_start";

    public static final String COMMAND_PREVIEW_URL_ATTRIBUTE_NAME = "previewUrl";
    public static final String COMMAND_GOAL_ATTRIBUTE_NAME        = "goal";

    public static final String WORKSPACE_STATUS_CHANGED_METHOD = "workspace/statusChanged";
    public static final String MACHINE_STATUS_CHANGED_METHOD   = "machine/statusChanged";
    public static final String SERVER_STATUS_CHANGED_METHOD    = "server/statusChanged";
    public static final String MACHINE_LOG_METHOD              = "machine/log";
    public static final String INSTALLER_LOG_METHOD            = "installer/log";

    public static final String LINK_REL_ENVIRONMENT_OUTPUT_CHANNEL = "environment.output_channel";
    public static final String ENVIRONMENT_OUTPUT_CHANNEL_TEMPLATE = "workspace:%s:environment_output";
    public static final String LINK_REL_ENVIRONMENT_STATUS_CHANNEL = "environment.status_channel";
    public static final String ENVIRONMENT_STATUS_CHANNEL_TEMPLATE = "workspace:%s:machines_statuses";

    public static final String WSAGENT_REFERENCE                   = "wsagent/http";
    public static final String WSAGENT_WEBSOCKET_REFERENCE         = "wsagent.websocket";
    public static final String WSAGENT_DEBUG_REFERENCE             = "wsagent.debug";

    public static final String TERMINAL_REFERENCE = "terminal";
    public static final String EXEC_AGENT_REFERENCE = "exec-agent/ws";

    public static final String WS_AGENT_PORT = "4401/tcp";

    public static final String WS_MACHINE_NAME = "default";

    public static final String ACTIVITY_CHECKER     = "activity-checker";

    private Constants() {}
}
