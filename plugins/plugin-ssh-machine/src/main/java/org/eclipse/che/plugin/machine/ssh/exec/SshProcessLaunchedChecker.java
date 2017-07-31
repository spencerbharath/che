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
package org.eclipse.che.plugin.machine.ssh.exec;

import static java.lang.String.format;

import org.eclipse.che.api.agent.shared.model.Agent;
import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.model.machine.Command;
import org.eclipse.che.api.core.util.ListLineConsumer;
import org.eclipse.che.api.machine.server.exception.MachineException;
import org.eclipse.che.api.machine.server.model.impl.CommandImpl;
import org.eclipse.che.plugin.machine.ssh.SshMachineInstance;
import org.eclipse.che.plugin.machine.ssh.SshMachineProcess;

/**
 * Verifies if a specific process is started on machine.
 *
 * @author Max Shaposhnik (mshaposhnik@codenvy.com)
 */
public class SshProcessLaunchedChecker {
  private static final String CHECK_COMMAND =
      "command -v pidof >/dev/null 2>&1 && {\n"
          + "    pidof %1$s >/dev/null 2>&1 && echo 0 || echo 1\n"
          + "} || {\n"
          + "    ps -fC %1$s >/dev/null 2>&1 && echo 0 || echo 1\n"
          + "}";
  private final String processNameToWait;
  private long counter;

  public SshProcessLaunchedChecker(String processNameToWait) {
    this.processNameToWait = processNameToWait;
  }

  public boolean isLaunched(Agent agent, SshMachineInstance machine) throws MachineException {
    Command command =
        new CommandImpl(
            format("Wait for %s, try %d", agent.getId(), ++counter),
            format(CHECK_COMMAND, processNameToWait),
            "test");

    try (ListLineConsumer lineConsumer = new ListLineConsumer()) {
      SshMachineProcess waitProcess = machine.createProcess(command, null);
      waitProcess.start(lineConsumer);
      return lineConsumer.getText().endsWith("[STDOUT] 0");
    } catch (ConflictException e) {
      throw new MachineException(e.getServiceError());
    }
  }
}
