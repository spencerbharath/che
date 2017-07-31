/**
 * ***************************************************************************** Copyright (c)
 * 2012-2017 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.api.testing.shared.messages;

/** Describes types of test messages. */
public final class TestingMessageNames {

  public static final String MESSAGE = "message";
  public static final String ROOT_PRESENTATION = "rootName";
  public static final String TEST_SUITE_STARTED = "testSuiteStarted";
  public static final String TEST_SUITE_FINISHED = "testSuiteFinished";
  public static final String TEST_STARTED = "testStarted";
  public static final String TEST_FINISHED = "testFinished";
  public static final String TEST_IGNORED = "testIgnored";
  public static final String TEST_STD_OUT = "testStdOut";
  public static final String TEST_STD_ERR = "testStdErr";
  public static final String TEST_FAILED = "testFailed";
  public static final String TEST_COUNT = "testCount";
  public static final String TEST_REPORTER_ATTACHED = "testReporterAttached";
  public static final String SUITE_TREE_STARTED = "suiteTreeStarted";
  public static final String SUITE_TREE_ENDED = "suiteTreeEnded";
  public static final String SUITE_TREE_NODE = "suiteTreeNode";
  public static final String BUILD_TREE_ENDED = "treeEnded";
  public static final String TESTING_STARTED = "testingStarted";
  public static final String FINISH_TESTING = "finishTesting";
  public static final String UNCAPTURED_OUTPUT = "uncapturedOutput";

  TestingMessageNames() {}
}
