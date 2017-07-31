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
package org.eclipse.che.ide.api.machine.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.eclipse.che.api.core.model.machine.Machine;

/** @author Dmitry Shnurenko */
public class ProcessFinishedEvent extends GwtEvent<ProcessFinishedEvent.Handler> {

  public interface Handler extends EventHandler {

    /** Implement this method to handle ProcessFinishedEvent. */
    void onProcessFinished(ProcessFinishedEvent event);
  }

  public static final Type<ProcessFinishedEvent.Handler> TYPE = new Type<>();

  private final int processID;
  private final Machine machine;

  public ProcessFinishedEvent(int processID, Machine machine) {
    this.processID = processID;
    this.machine = machine;
  }

  public int getProcessID() {
    return processID;
  }

  public Machine getMachine() {
    return machine;
  }

  @Override
  public Type<ProcessFinishedEvent.Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onProcessFinished(this);
  }
}
