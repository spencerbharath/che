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
package org.eclipse.che.ide.websocket.impl;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * A place to store and manage action sets for connection related events like
 * connection established or connection closed.
 */
@Singleton
public class WebSocketActionManager {
    private final Map<String, Set<Runnable>> establishActions = new HashMap<>();
    private final Map<String, Set<Runnable>> closeActions     = new HashMap<>();

    /**
     * Add a set of actions to be performed on each connection to a specified url
     *
     * @param url
     *         url of a connection
     * @param actions
     *         actions set
     */
    public void setOnEstablishActions(String url, Set<Runnable> actions) {
        establishActions.put(url, actions);
    }

    /**
     * Gets all establish actions associated with mentioned url
     *
     * @param url
     *         url of a connection
     * @return set of actions
     */
    public Set<Runnable> getOnOpenActions(String url) {
        return establishActions.getOrDefault(url, emptySet());
    }

    /**
     * Clear a set of actions to be performed on each connection to a specified url
     *
     * @param url
     *         url of a connection
     */
    public void clearOnEstablishActions(String url) {
        establishActions.remove(url);
    }

    /**
     * Add a set of actions to be performed on each connection closed to a specified url
     *
     * @param url
     *         url of a connection
     * @param actions
     *         actions set
     */
    public void setOnCloseActions(String url, Set<Runnable> actions) {
        closeActions.put(url, actions);
    }

    /**
     * Gets all close actions associated with mentioned url
     *
     * @param url
     *         url of a connection
     * @return set of actions
     */
    public Set<Runnable> getOnCloseActions(String url) {
        return closeActions.getOrDefault(url, emptySet());
    }

    /**
     * Clear a set of actions to be performed on each connection closed to a specified url
     *
     * @param url
     *         url of a connection
     */
    public void clearOnCloseActions(String url) {
        closeActions.remove(url);
    }
}
