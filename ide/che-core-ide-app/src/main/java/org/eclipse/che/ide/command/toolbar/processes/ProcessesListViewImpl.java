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
package org.eclipse.che.ide.command.toolbar.processes;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.eclipse.che.ide.command.CommandResources;
import org.eclipse.che.ide.command.toolbar.ToolbarMessages;
import org.eclipse.che.ide.ui.dropdown.BaseListItem;
import org.eclipse.che.ide.ui.dropdown.DropdownList;
import org.eclipse.che.ide.ui.dropdown.StringItemRenderer;

/** Implementation of {@link ProcessesListView} that displays processes in a dropdown list. */
@Singleton
public class ProcessesListViewImpl implements ProcessesListView {

  private final Map<Process, BaseListItem<Process>> listItems;
  private final Map<Process, ProcessItemRenderer> renderers;
  private final FlowPanel rootPanel;
  private final DropdownList dropdownList;
  private final EmptyListWidget emptyListWidget;
  private final ToolbarMessages messages;
  private final CreateCommandItem createCommandItem;
  private final CreateCommandItemRenderer createCommandItemRenderer;

  private ActionDelegate delegate;

  @Inject
  public ProcessesListViewImpl(
      CommandResources resources, EmptyListWidget emptyListWidget, ToolbarMessages messages) {
    this.emptyListWidget = emptyListWidget;
    this.messages = messages;

    listItems = new HashMap<>();
    renderers = new HashMap<>();

    final Label label = new Label("EXEC");
    label.addStyleName(resources.commandToolbarCss().processesListLabel());

    dropdownList = new DropdownList(emptyListWidget, true);
    dropdownList.setWidth("100%");
    dropdownList.ensureDebugId("dropdown-processes");
    dropdownList.setSelectionHandler(
        item -> {
          if (item instanceof CreateCommandItem) {
            delegate.onCreateCommand();
          } else {
            listItems
                .entrySet()
                .stream()
                .filter(entry -> item.equals(entry.getValue()))
                .forEach(entry -> delegate.onProcessChosen(entry.getKey()));
          }
        });

    rootPanel = new FlowPanel();
    rootPanel.add(label);
    rootPanel.add(dropdownList);

    createCommandItem = new CreateCommandItem();
    createCommandItemRenderer = new CreateCommandItemRenderer();
    checkCreateCommandItem();
  }

  /**
   * Ensures that item for creating command added to the empty list or removed from non empty list.
   */
  private void checkCreateCommandItem() {
    if (listItems.isEmpty()) {
      dropdownList.addItem(createCommandItem, createCommandItemRenderer);
    } else {
      dropdownList.removeItem(createCommandItem);
    }
  }

  @Override
  public void setDelegate(ActionDelegate delegate) {
    this.delegate = delegate;
  }

  @Override
  public Widget asWidget() {
    return rootPanel;
  }

  @Override
  public void clearList() {
    dropdownList.clear();

    checkCreateCommandItem();
  }

  @Override
  public void processStopped(Process process) {
    final ProcessItemRenderer renderer = renderers.get(process);

    if (renderer != null) {
      renderer.notifyProcessStopped();
    }
  }

  @Override
  public void addProcess(Process process) {
    final BaseListItem<Process> listItem = new BaseListItem<>(process);
    final ProcessItemRenderer renderer =
        new ProcessItemRenderer(
            listItem, p -> delegate.onStopProcess(p), p -> delegate.onReRunProcess(p));

    listItems.put(process, listItem);
    renderers.put(process, renderer);

    dropdownList.addItem(listItem, renderer);

    checkCreateCommandItem();
  }

  @Override
  public void removeProcess(Process process) {
    final BaseListItem<Process> listItem = listItems.get(process);

    if (listItem != null) {
      listItems.remove(process);
      renderers.remove(process);

      dropdownList.removeItem(listItem);

      checkCreateCommandItem();
    }
  }

  private class CreateCommandItem extends BaseListItem<String> {
    CreateCommandItem() {
      super(messages.guideItemLabel("new"));
    }
  }

  private class CreateCommandItemRenderer extends StringItemRenderer {
    CreateCommandItemRenderer() {
      super(createCommandItem);
    }

    @Override
    public Widget renderHeaderWidget() {
      return emptyListWidget;
    }
  }
}
