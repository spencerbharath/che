/**
 * ***************************************************************************** Copyright (c)
 * 2016-2017 Rogue Wave Software, Inc. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Rogue Wave Software, Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.composer.server.executor;

import org.eclipse.che.plugin.composer.shared.dto.ComposerOutput;

/** @author Kaloyan Raev */
public class ComposerOutputImpl implements ComposerOutput {

  private String output;
  private State state;

  public ComposerOutputImpl(String output, State state) {
    this.output = output;
    this.state = state;
  }

  @Override
  public String getOutput() {
    return output;
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public void setOutput(String output) {
    this.output = output;
  }

  @Override
  public void setState(State state) {
    this.state = state;
  }
}
