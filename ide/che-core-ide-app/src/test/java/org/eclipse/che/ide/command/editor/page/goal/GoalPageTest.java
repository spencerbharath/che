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
package org.eclipse.che.ide.command.editor.page.goal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.che.ide.api.command.CommandGoal;
import org.eclipse.che.ide.api.command.CommandGoalRegistry;
import org.eclipse.che.ide.api.command.CommandImpl;
import org.eclipse.che.ide.api.command.CommandImpl.ApplicableContext;
import org.eclipse.che.ide.api.dialogs.CancelCallback;
import org.eclipse.che.ide.api.dialogs.DialogFactory;
import org.eclipse.che.ide.api.dialogs.InputCallback;
import org.eclipse.che.ide.api.dialogs.InputDialog;
import org.eclipse.che.ide.command.editor.EditorMessages;
import org.eclipse.che.ide.command.editor.page.CommandEditorPage.DirtyStateListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/** Tests for {@link GoalPage}. */
@RunWith(MockitoJUnitRunner.class)
public class GoalPageTest {

  private static final String COMMAND_GOAL_ID = "build";

  @Mock private GoalPageView view;
  @Mock private CommandGoalRegistry goalRegistry;
  @Mock private EditorMessages messages;
  @Mock private DialogFactory dialogFactory;

  @InjectMocks private GoalPage page;

  @Mock private DirtyStateListener dirtyStateListener;
  @Mock private CommandImpl editedCommand;
  @Mock private ApplicableContext editedCommandApplicableContext;

  @Before
  public void setUp() throws Exception {
    CommandGoal goal = mock(CommandGoal.class);
    when(goal.getId()).thenReturn(COMMAND_GOAL_ID);
    when(goalRegistry.getGoalForId(anyString())).thenReturn(goal);

    when(editedCommand.getApplicableContext()).thenReturn(editedCommandApplicableContext);
    when(editedCommand.getGoal()).thenReturn(COMMAND_GOAL_ID);

    page.setDirtyStateListener(dirtyStateListener);
    page.edit(editedCommand);
  }

  @Test
  public void shouldSetViewDelegate() throws Exception {
    verify(view).setDelegate(page);
  }

  @Test
  public void shouldInitializeView() throws Exception {
    verify(goalRegistry).getAllGoals();
    verify(view).setAvailableGoals(Matchers.<CommandGoal>anySet());
    verify(view).setGoal(eq(COMMAND_GOAL_ID));
  }

  @Test
  public void shouldReturnView() throws Exception {
    assertEquals(view, page.getView());
  }

  @Test
  public void shouldNotifyListenerWhenGoalChanged() throws Exception {
    page.onGoalChanged("test");

    verify(dirtyStateListener, times(2)).onDirtyStateChanged();
  }

  @Test
  public void shouldCreateGoal() throws Exception {
    // given
    InputDialog inputDialog = mock(InputDialog.class);
    when(dialogFactory.createInputDialog(
            anyString(),
            anyString(),
            anyString(),
            eq(0),
            eq(0),
            anyString(),
            any(InputCallback.class),
            any(CancelCallback.class)))
        .thenReturn(inputDialog);
    String newGoalId = "new goal";

    // when
    page.onCreateGoal();

    // then
    ArgumentCaptor<InputCallback> inputCaptor = ArgumentCaptor.forClass(InputCallback.class);
    verify(dialogFactory)
        .createInputDialog(
            anyString(),
            anyString(),
            anyString(),
            eq(0),
            eq(0),
            anyString(),
            inputCaptor.capture(),
            isNull(CancelCallback.class));
    verify(inputDialog).show();
    inputCaptor.getValue().accepted(newGoalId);
    verify(view).setGoal(eq(newGoalId));
    verify(editedCommand).setGoal(eq(newGoalId));
    verify(dirtyStateListener, times(2)).onDirtyStateChanged();
  }
}
