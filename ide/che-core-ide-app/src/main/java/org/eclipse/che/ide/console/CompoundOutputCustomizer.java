/**
 * ***************************************************************************** Copyright (c) 2017
 * Red Hat, Inc. All rights reserved. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Red Hat, Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.ide.console;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Compound customizer allows to combine several different customizers. A text provided will be
 * treated as customize if at least one of nested customizers detects it as customizable
 *
 * @author Victor Rubezhny
 */
public class CompoundOutputCustomizer implements OutputCustomizer {
  private OutputCustomizer[] customizers = null;

  /**
   * Constructs the compound customizer object from a number of specified output customizers
   *
   * @param customizers
   */
  public CompoundOutputCustomizer(OutputCustomizer... customizers) {
    this.customizers = customizers;
  }

  @Override
  public boolean canCustomize(String text) {
    return Stream.of(customizers).anyMatch(customizer -> customizer.canCustomize(text));
  }

  @Override
  public String customize(String text) {
    Optional<OutputCustomizer> optional =
        Stream.of(customizers).filter(customizer -> customizer.canCustomize(text)).findFirst();
    return optional.isPresent() ? optional.get().customize(text) : text;
  }
}
