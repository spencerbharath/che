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
package org.eclipse.che.ide.search.presentation;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import org.apache.regexp.RE;
import org.eclipse.che.api.project.shared.SearchOccurrence;
import org.eclipse.che.api.project.shared.dto.SearchResultDto;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.js.Promises;
import org.eclipse.che.ide.CoreLocalizationConstant;
import org.eclipse.che.ide.Resources;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.data.tree.AbstractTreeNode;
import org.eclipse.che.ide.api.data.tree.Node;
import org.eclipse.che.ide.api.editor.EditorAgent;
import org.eclipse.che.ide.api.resources.File;
import org.eclipse.che.ide.api.resources.SearchResult;
import org.eclipse.che.ide.resource.Path;
import org.eclipse.che.ide.resources.impl.FileImpl;
import org.eclipse.che.ide.resources.impl.ResourceManager;
import org.eclipse.che.ide.resources.tree.FileNode;
import org.eclipse.che.ide.resources.tree.ResourceNode;
import org.eclipse.che.ide.ui.smartTree.TreeStyles;
import org.eclipse.che.ide.ui.smartTree.compare.NameComparator;
import org.eclipse.che.ide.ui.smartTree.presentation.HasPresentation;
import org.eclipse.che.ide.ui.smartTree.presentation.NodePresentation;
import org.eclipse.che.ide.util.Pair;

import javax.inject.Provider;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.eclipse.che.ide.api.theme.Style.getEditorInfoTextColor;

/**
 * Tree node represent search result.
 *
 * @author Valeriy Svydenko
 * @author Vlad Zhukovskyi
 */
public class FindResultGroupNode extends AbstractTreeNode implements HasPresentation {

    private AppContext appContext;
    private EditorAgent editorAgent;
    private final CoreLocalizationConstant locale;
    private       TreeStyles               styles;
    private       Resources                resources;
    private final ResourceNode.NodeFactory nodeFactory;

    private NodePresentation   nodePresentation;
    private List<SearchResult> findResults;
    private String             request;

    @Inject
    public FindResultGroupNode(AppContext appContext,
                               EditorAgent editorAgent,
                               CoreLocalizationConstant locale,
                               TreeStyles styles,
                               Resources resources,
                               ResourceNode.NodeFactory nodeFactory,
                               @Assisted List<SearchResult> findResult,
                               @Assisted String request) {
        this.appContext = appContext;
        this.editorAgent = editorAgent;
        this.locale = locale;
        this.styles = styles;
        this.resources = resources;
        this.nodeFactory = nodeFactory;
        this.findResults = findResult;
        this.request = request;
    }

    /** {@inheritDoc} */
    @Override
    protected Promise<List<Node>> getChildrenImpl() {
        List<Node> fileNodes = new ArrayList<>();
        for (SearchResult searchResult : findResults) {
                FoundItemNode foundItemNode = new FoundItemNode(appContext, editorAgent, styles, resources, searchResult, request);
                NodePresentation presentation = foundItemNode.getPresentation(true);
                presentation.setInfoText(searchResult.getPath());
                presentation.setInfoTextWrapper(Pair.of("(", ")"));
                presentation.setInfoTextCss("color:" + getEditorInfoTextColor() + ";font-size: 11px");

                fileNodes.add(foundItemNode);
            }
        //sort nodes by file name
        Collections.sort(fileNodes, new NameComparator());

        return Promises.resolve(fileNodes);
    }

    /** {@inheritDoc} */
    @Override
    public NodePresentation getPresentation(boolean update) {
        if (nodePresentation == null) {
            nodePresentation = new NodePresentation();
            updatePresentation(nodePresentation);
        }

        if (update) {
            updatePresentation(nodePresentation);
        }
        return nodePresentation;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return locale.actionFullTextSearch();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLeaf() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void updatePresentation(@NotNull NodePresentation presentation) {
        int num = 0;
        for(SearchResult searchResult : findResults) {
            num += searchResult.getOccurrences().size();
        }
        StringBuilder resultTitle = new StringBuilder("Find Occurrences of '" + request + "\'  (" + num + " occurrence");
        if (num > 1) {
            resultTitle.append("s)");
        } else {
            resultTitle.append(")");
        }
        presentation.setPresentableText(resultTitle.toString());
    }

}
