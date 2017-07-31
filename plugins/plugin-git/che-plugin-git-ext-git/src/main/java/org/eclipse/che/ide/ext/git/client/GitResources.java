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
package org.eclipse.che.ide.ext.git.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import org.eclipse.che.ide.ext.git.client.importer.page.GitImporterPageViewImpl;
import org.vectomatic.dom.svg.ui.SVGResource;

/**
 * @author Ann Zhuleva
 * @author Vlad Zhukovskyi
 */
public interface GitResources extends ClientBundle {

  interface GitCSS extends CssResource {
    String textFont();

    String cells();

    String simpleListContainer();

    String emptyBorder();

    String spacing();
  }

  @Source({"importer/page/GitImporterPage.css", "org/eclipse/che/ide/api/ui/style.css"})
  GitImporterPageViewImpl.Style gitImporterPageStyle();

  @Source({"git.css", "org/eclipse/che/ide/api/ui/style.css"})
  GitCSS gitCSS();

  @Source("push/arrow.svg")
  SVGResource arrow();

  @Source("controls/init.svg")
  SVGResource initRepo();

  @Source("controls/delete-repo.svg")
  SVGResource deleteRepo();

  @Source("controls/merge.svg")
  SVGResource merge();

  @Source("controls/branches.svg")
  SVGResource branches();

  @Source("controls/remotes.svg")
  SVGResource remotes();

  @Source("controls/commit.svg")
  SVGResource commit();

  @Source("controls/push.svg")
  SVGResource push();

  @Source("controls/pull.svg")
  SVGResource pull();

  @Source("controls/checkoutReference.svg")
  SVGResource checkoutReference();

  @Source("history/history.svg")
  SVGResource history();

  @Source("history/project_level.svg")
  SVGResource projectLevel();

  @Source("history/resource_level.svg")
  SVGResource resourceLevel();

  @Source("history/diff_index.svg")
  SVGResource diffIndex();

  @Source("history/diff_working_dir.svg")
  SVGResource diffWorkTree();

  @Source("history/diff_prev_version.svg")
  SVGResource diffPrevVersion();

  @Source("history/refresh.svg")
  SVGResource refresh();

  @Source("controls/fetch.svg")
  SVGResource fetch();

  @Source("branch/current.svg")
  SVGResource currentBranch();

  @Source("controls/remote.svg")
  SVGResource remote();

  @Source("controls/git-output-icon.svg")
  SVGResource gitOutput();
}
