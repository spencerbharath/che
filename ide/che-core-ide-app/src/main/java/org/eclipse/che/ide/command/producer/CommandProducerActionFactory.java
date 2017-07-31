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
package org.eclipse.che.ide.command.producer;

import org.eclipse.che.api.core.model.machine.Machine;
import org.eclipse.che.ide.api.command.CommandProducer;

/**
 * Factory for creating {@link CommandProducerAction} instances.
 *
 * @author Artem Zatsarynnyi
 */
public interface CommandProducerActionFactory {

  /** Creates action for executing command produced by the specified {@code commandProducer}. */
  CommandProducerAction create(String name, CommandProducer commandProducer, Machine machine);
}
