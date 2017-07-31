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
package org.eclipse.che.plugin.testing.testng.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * Utility class for creating TestNG suite. A suite is represented by one XML file. It can contain
 * one or more tests.
 */
@Singleton
public class TestNGSuiteUtil {

  private static final Logger LOG = LoggerFactory.getLogger(TestNGSuiteUtil.class);

  /**
   * Creates the suite which is represented by one XML file with name che-testng-suite.xml. It can
   * contain one or more tests.
   *
   * @param suitePath path to the suite file
   * @param suiteName the name of the suite
   * @param classesAndMethods classes and methods which should be included to the suite.
   * @return created file with suite
   */
  public File writeSuite(
      String suitePath, String suiteName, Map<String, List<String>> classesAndMethods) {
    XmlSuite suite = new XmlSuite();
    XmlTest test = new XmlTest(suite);
    test.setName(suiteName);
    List<XmlClass> xmlClasses = new ArrayList<>();

    for (String className : classesAndMethods.keySet()) {
      XmlClass xmlClass = new XmlClass(className, false);
      xmlClasses.add(xmlClass);
      List<String> methods = classesAndMethods.get(className);

      if (methods != null) {
        List<XmlInclude> includedMethods = new ArrayList<>();
        for (String method : methods) {
          includedMethods.add(new XmlInclude(method));
        }

        xmlClass.setIncludedMethods(includedMethods);
      }
    }

    test.setXmlClasses(xmlClasses);

    File result = new File(suitePath, "che-testng-suite.xml");
    try {
      com.google.common.io.Files.write(suite.toXml().getBytes("UTF-8"), result);
    } catch (IOException e) {
      LOG.error("Can't write TestNG suite xml file.", e);
    }
    return result;
  }
}
