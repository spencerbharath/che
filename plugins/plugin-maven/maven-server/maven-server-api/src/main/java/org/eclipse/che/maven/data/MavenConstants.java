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
package org.eclipse.che.maven.data;

/**
 * Contains some well known maven constants
 *
 * @author Evgen Vidolob
 */
public interface MavenConstants {
  String SNAPSHOT = "SNAPSHOT";
  String LATEST = "LATEST";
  String RELEASE = "RELEASE";
  String POM_EXTENSION = "pom";
  String PROFILE_FROM_POM = "pom";
  String POM_FILE_NAME = "pom.xml";
}
