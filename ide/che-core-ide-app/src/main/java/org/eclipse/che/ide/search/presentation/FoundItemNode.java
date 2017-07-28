package org.eclipse.che.ide.search.presentation;

import org.eclipse.che.api.project.shared.SearchOccurrence;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.js.Promises;
import org.eclipse.che.ide.Resources;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.data.tree.AbstractTreeNode;
import org.eclipse.che.ide.api.data.tree.HasAction;
import org.eclipse.che.ide.api.data.tree.Node;
import org.eclipse.che.ide.api.editor.EditorAgent;
import org.eclipse.che.ide.api.resources.SearchResult;
import org.eclipse.che.ide.ui.smartTree.TreeStyles;
import org.eclipse.che.ide.ui.smartTree.compare.NameComparator;
import org.eclipse.che.ide.ui.smartTree.presentation.HasPresentation;
import org.eclipse.che.ide.ui.smartTree.presentation.NodePresentation;
import org.eclipse.che.ide.util.Pair;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.eclipse.che.ide.api.theme.Style.getEditorInfoTextColor;

/**
 * @author Vitalii Parfonov
 */

public class FoundItemNode extends AbstractTreeNode implements HasPresentation, HasAction {


    private NodePresentation nodePresentation;
    private AppContext       appContext;
    private EditorAgent editorAgent;
    private TreeStyles styles;
    private Resources        resources;
    private SearchResult searchResult;
    private String request;

    public FoundItemNode(AppContext appContext,
                         EditorAgent editorAgent,
                         TreeStyles styles,
                         Resources resources,
                         SearchResult searchResult,
                         String request) {
        this.appContext = appContext;
        this.editorAgent = editorAgent;
        this.styles = styles;
        this.resources = resources;
        this.searchResult = searchResult;
        this.request = request;
    }

    @Override
    public void actionPerformed() {

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
        return searchResult.getName();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLeaf() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void updatePresentation(@NotNull NodePresentation presentation) {
        StringBuilder resultTitle = new StringBuilder("Find Occurrences of '" + request + "\'  (" + searchResult.getOccurrences().size() + " occurrence");
        resultTitle.append(searchResult.getName() + " :: " + searchResult.getPath());
        if (searchResult.getOccurrences().size() > 1) {
            resultTitle.append("s)");
        } else {
            resultTitle.append(")");
        }
        presentation.setPresentableText(resultTitle.toString());
    }

    @Override
    protected Promise<List<Node>> getChildrenImpl() {
        List<Node> fileNodes = new ArrayList<>();
            for (SearchOccurrence occurrence : searchResult.getOccurrences()) {
                FoundOccurrenceNode foundItemNode = new FoundOccurrenceNode(appContext, styles, resources, editorAgent, searchResult.getPath(), searchResult.getContentUrl(), occurrence);

                NodePresentation presentation = foundItemNode.getPresentation(true);
                presentation.setInfoText(occurrence.getPhrase());
                presentation.setInfoTextWrapper(Pair.of("(", ")"));
                presentation.setInfoTextCss("color:" + getEditorInfoTextColor() + ";font-size: 11px");

                fileNodes.add(foundItemNode);
            }

        //sort nodes by file name
        Collections.sort(fileNodes, new NameComparator());

        return Promises.resolve(fileNodes);
    }
}
