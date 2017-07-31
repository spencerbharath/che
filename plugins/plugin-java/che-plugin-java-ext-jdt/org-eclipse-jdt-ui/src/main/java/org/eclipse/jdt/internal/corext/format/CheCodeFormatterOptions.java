/**
 * ***************************************************************************** Copyright (c)
 * 2012-2015 Codenvy, S.A. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Codenvy, S.A. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.jdt.internal.corext.format;

import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/** @author Roman Nikitenko */
public class CheCodeFormatterOptions {
  private static final Logger LOG = LoggerFactory.getLogger(CheCodeFormatterInitializer.class);
  private static final String DEFAULT_CODESTYLE = "che-codestyle-eclipse_.xml";
  private static Map<String, String> formatSettings;

  public static Map<String, String> getDefaultFormatSettings() {
    if (formatSettings != null && !formatSettings.isEmpty()) {
      return formatSettings;
    }

    formatSettings = new CheCodeFormatterOptions().getCheDefaultSettings();
    if (formatSettings != null && !formatSettings.isEmpty()) {
      return formatSettings;
    }
    return DefaultCodeFormatterConstants.getEclipseDefaultSettings();
  }

  private Map<String, String> getCheDefaultSettings() {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    XMLParser parserXML = new XMLParser();
    try {
      SAXParser parser = factory.newSAXParser();
      parser.parse(getClass().getResourceAsStream(DEFAULT_CODESTYLE), parserXML);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      LOG.error("It is not possible to parse file " + DEFAULT_CODESTYLE, e);
    }
    return parserXML.getSettings();
  }
}
