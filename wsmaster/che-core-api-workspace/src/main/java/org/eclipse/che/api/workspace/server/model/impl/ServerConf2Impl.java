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
package org.eclipse.che.api.workspace.server.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import org.eclipse.che.api.core.model.workspace.ServerConf2;

/** @author Alexander Garagatyi */
@Entity(name = "ServerConf")
@Table(name = "serverconf")
public class ServerConf2Impl implements ServerConf2 {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "port")
  private String port;

  @Column(name = "protocol")
  private String protocol;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
    name = "serverconf_properties",
    joinColumns = @JoinColumn(name = "serverconf_id")
  )
  @MapKeyColumn(name = "properties_key")
  @Column(name = "properties")
  private Map<String, String> properties;

  public ServerConf2Impl() {}

  public ServerConf2Impl(String port, String protocol, Map<String, String> properties) {
    this.port = port;
    this.protocol = protocol;
    if (properties != null) {
      this.properties = new HashMap<>(properties);
    }
  }

  public ServerConf2Impl(ServerConf2 serverConf) {
    this.port = serverConf.getPort();
    this.protocol = serverConf.getProtocol();
    if (serverConf.getProperties() != null) {
      this.properties = new HashMap<>(serverConf.getProperties());
    }
  }

  @Override
  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  @Override
  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  @Override
  public Map<String, String> getProperties() {
    if (properties == null) {
      properties = new HashMap<>();
    }
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ServerConf2Impl)) {
      return false;
    }
    final ServerConf2Impl that = (ServerConf2Impl) obj;
    return Objects.equals(id, that.id)
        && Objects.equals(port, that.port)
        && Objects.equals(protocol, that.protocol)
        && getProperties().equals(that.getProperties());
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + Objects.hashCode(id);
    hash = 31 * hash + Objects.hashCode(port);
    hash = 31 * hash + Objects.hashCode(protocol);
    hash = 31 * hash + getProperties().hashCode();
    return hash;
  }

  @Override
  public String toString() {
    return "ServerConf2Impl{"
        + "id="
        + id
        + ", port='"
        + port
        + '\''
        + ", protocol='"
        + protocol
        + '\''
        + ", properties="
        + properties
        + '}';
  }
}
