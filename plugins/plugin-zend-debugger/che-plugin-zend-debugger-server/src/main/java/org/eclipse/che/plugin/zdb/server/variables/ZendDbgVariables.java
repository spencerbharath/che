/**
 * ***************************************************************************** Copyright (c) 2016
 * Rogue Wave Software, Inc. All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Rogue Wave Software, Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.zdb.server.variables;

import static org.eclipse.che.plugin.zdb.server.expressions.IDbgDataFacet.Facet.KIND_LOCAL;
import static org.eclipse.che.plugin.zdb.server.expressions.IDbgDataFacet.Facet.KIND_SUPER_GLOBAL;
import static org.eclipse.che.plugin.zdb.server.expressions.IDbgDataFacet.Facet.KIND_THIS;

import java.util.Collections;
import org.eclipse.che.plugin.zdb.server.expressions.ZendDbgExpression;
import org.eclipse.che.plugin.zdb.server.expressions.ZendDbgExpressionEvaluator;
import org.eclipse.che.plugin.zdb.server.utils.ZendDbgVariableUtils;

/**
 * Expression for fetching current stack frame variables.
 *
 * @author Bartlomiej Laczkowski
 */
public class ZendDbgVariables extends ZendDbgExpression {

  private static final String DUMP_VARIABLES_EXPRESSION =
      "eval('if (isset($this)) {$this;}; return get_defined_vars();')";
  private static final String SIGIL = "$";

  public ZendDbgVariables(ZendDbgExpressionEvaluator expressionEvaluator) {
    super(expressionEvaluator, DUMP_VARIABLES_EXPRESSION, Collections.emptyList());
  }

  @Override
  protected ZendDbgExpression createChild(String variableName, Facet... facets) {
    variableName = SIGIL + variableName;
    Facet facet = KIND_LOCAL;
    if (ZendDbgVariableUtils.isThis(variableName)) facet = KIND_THIS;
    else if (ZendDbgVariableUtils.isSuperGlobal(variableName)) facet = KIND_SUPER_GLOBAL;
    return new ZendDbgExpression(
        getExpressionEvaluator(), variableName, Collections.singletonList(variableName), facet);
  }
}
