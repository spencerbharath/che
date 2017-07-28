package org.eclipse.che.ide.search.presentation;

import elemental.html.SpanElement;

import com.google.common.base.Optional;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;

import org.eclipse.che.api.project.shared.SearchOccurrence;
import org.eclipse.che.api.promises.client.Operation;
import org.eclipse.che.api.promises.client.OperationException;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.ide.Resources;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.data.tree.AbstractTreeNode;
import org.eclipse.che.ide.api.data.tree.HasAction;
import org.eclipse.che.ide.api.data.tree.Node;
import org.eclipse.che.ide.api.editor.EditorAgent;
import org.eclipse.che.ide.api.editor.EditorPartPresenter;
import org.eclipse.che.ide.api.editor.OpenEditorCallbackImpl;
import org.eclipse.che.ide.api.editor.text.LinearRange;
import org.eclipse.che.ide.api.editor.texteditor.TextEditor;
import org.eclipse.che.ide.api.resources.File;
import org.eclipse.che.ide.api.resources.Project;
import org.eclipse.che.ide.api.resources.Resource;
import org.eclipse.che.ide.api.resources.SyntheticFile;
import org.eclipse.che.ide.api.resources.VirtualFile;
import org.eclipse.che.ide.resource.Path;
import org.eclipse.che.ide.ui.smartTree.TreeStyles;
import org.eclipse.che.ide.ui.smartTree.presentation.HasPresentation;
import org.eclipse.che.ide.ui.smartTree.presentation.NodePresentation;
import org.eclipse.che.ide.util.dom.Elements;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Vitalii Parfonov
 */

public class FoundOccurrenceNode extends AbstractTreeNode implements HasPresentation, HasAction {


    private NodePresentation nodePresentation;
    private AppContext       appContext;
    private TreeStyles styles;
    private Resources        resources;
    private EditorAgent      editorAgent;
    private String path;
    private String           conentUrl;
    private SearchOccurrence searchOccurrence;

    public FoundOccurrenceNode(AppContext appContext,
                               TreeStyles styles,
                               Resources resources,
                               EditorAgent editorAgent,
                               String path,
                               String conentUrl,
                               SearchOccurrence searchOccurrence) {
        this.appContext = appContext;
        this.styles = styles;
        this.resources = resources;
        this.editorAgent = editorAgent;
        this.path = path;
        this.conentUrl = conentUrl;
        this.searchOccurrence = searchOccurrence;
    }

    @Override
    public void actionPerformed() {
            final EditorPartPresenter editorPartPresenter = editorAgent.getOpenedEditor(Path.valueOf(path));
            if (editorPartPresenter != null) {
                selectRange(editorPartPresenter);
                Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                    @Override
                    public void execute() {
                        editorAgent.activateEditor(editorPartPresenter);
                    }
                });
                return;
            }

            appContext.getWorkspaceRoot().getFile(path).then(new Operation<Optional<File>>() {
                @Override
                public void apply(Optional<File> file) throws OperationException {
                    if (file.isPresent()) {
                        editorAgent.openEditor(file.get(), new OpenEditorCallbackImpl() {
                            @Override
                            public void onEditorOpened(EditorPartPresenter editor) {
                                selectRange(editor);
                            }
                        });
                    }
                }
            });
        }

    private void selectRange(EditorPartPresenter editor) {
        if (editor instanceof TextEditor) {
            ((TextEditor)editor).getDocument().setSelectedRange(
                    LinearRange.createWithStart(searchOccurrence.getStartOffset()).andEnd(searchOccurrence.getEndOffset()),
                    true);
        }
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
        return searchOccurrence.getPhrase();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLeaf() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void updatePresentation(@NotNull NodePresentation presentation) {



        StringBuilder resultTitle = new StringBuilder("[" + searchOccurrence.getLineNumber() + "]" + searchOccurrence.getLineContent());
        presentation.setPresentableText(resultTitle.toString());


        SpanElement spanElement = Elements.createSpanElement(styles.styles().presentableTextContainer());

        SpanElement lineNumberElement = Elements.createSpanElement();
        lineNumberElement.setInnerHTML(String.valueOf(searchOccurrence.getLineNumber() + 1) + ":&nbsp;&nbsp;&nbsp;");
        spanElement.appendChild(lineNumberElement);

        SpanElement textElement = Elements.createSpanElement();
        String phrase = searchOccurrence.getPhrase();
        String matchedLine = searchOccurrence.getLineContent();
        if (matchedLine != null && phrase != null) {
            String startLine = matchedLine.substring(0, matchedLine.indexOf(phrase));
            textElement.appendChild(Elements.createTextNode(startLine));
            SpanElement highlightElement = Elements.createSpanElement(resources.coreCss().searchMatch());
            highlightElement
                    .setInnerText(matchedLine.substring(matchedLine.indexOf(phrase), matchedLine.indexOf(phrase) + phrase.length()));
            textElement.appendChild(highlightElement);

            textElement.appendChild(Elements.createTextNode(matchedLine.substring(matchedLine.indexOf(phrase) + phrase.length())));
        } else {
            textElement.appendChild(Elements.createTextNode("Can't find sources"));
        }
        spanElement.appendChild(textElement);

        presentation.setPresentableIcon(resources.searchMatch());
        presentation.setUserElement((Element)spanElement);

    }

    @Override
    protected Promise<List<Node>> getChildrenImpl() {
        return null;
    }
}
