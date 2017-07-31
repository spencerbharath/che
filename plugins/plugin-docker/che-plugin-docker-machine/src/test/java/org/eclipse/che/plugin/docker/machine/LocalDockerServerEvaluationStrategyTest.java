/**
 * ***************************************************************************** Copyright (c)
 * 2016-2017 Red Hat Inc. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Red Hat Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.docker.machine;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.che.api.core.model.machine.MachineConfig;
import org.eclipse.che.api.machine.server.model.impl.ServerConfImpl;
import org.eclipse.che.api.machine.server.model.impl.ServerImpl;
import org.eclipse.che.api.machine.server.model.impl.ServerPropertiesImpl;
import org.eclipse.che.plugin.docker.client.json.ContainerConfig;
import org.eclipse.che.plugin.docker.client.json.ContainerInfo;
import org.eclipse.che.plugin.docker.client.json.NetworkSettings;
import org.eclipse.che.plugin.docker.client.json.PortBinding;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MockitoTestNGListener.class)
public class LocalDockerServerEvaluationStrategyTest {

  private static final String CHE_DOCKER_IP = "container-host.com";
  private static final String CHE_DOCKER_IP_EXTERNAL = "container-host-ext.com";
  private static final String ALL_IP_ADDRESS = "0.0.0.0";
  private static final String CONTAINERINFO_GATEWAY = "172.17.0.1";
  private static final String CONTAINERINFO_IP_ADDRESS = "172.17.0.200";
  private static final String DEFAULT_HOSTNAME = "localhost";

  @Mock private ContainerInfo containerInfo;
  @Mock private MachineConfig machineConfig;
  @Mock private ContainerConfig containerConfig;
  @Mock private NetworkSettings networkSettings;

  private ServerEvaluationStrategy strategy;

  private Map<String, ServerConfImpl> serverConfs;

  private Map<String, List<PortBinding>> ports;

  @BeforeMethod
  public void setUp() {

    serverConfs = new HashMap<>();
    serverConfs.put(
        "4301/tcp", new ServerConfImpl("sysServer1-tcp", "4301/tcp", "http", "/some/path1"));
    serverConfs.put(
        "4305/udp", new ServerConfImpl("devSysServer1-udp", "4305/udp", null, "some/path4"));

    ports = new HashMap<>();
    ports.put(
        "4301/tcp",
        Collections.singletonList(
            new PortBinding().withHostIp(ALL_IP_ADDRESS).withHostPort("32100")));
    ports.put(
        "4305/udp",
        Collections.singletonList(
            new PortBinding().withHostIp(ALL_IP_ADDRESS).withHostPort("32103")));

    when(containerInfo.getNetworkSettings()).thenReturn(networkSettings);
    when(networkSettings.getGateway()).thenReturn(CONTAINERINFO_GATEWAY);
    when(networkSettings.getIpAddress()).thenReturn(CONTAINERINFO_IP_ADDRESS);
    when(networkSettings.getPorts()).thenReturn(ports);
    when(containerInfo.getConfig()).thenReturn(containerConfig);
    when(containerConfig.getLabels()).thenReturn(Collections.emptyMap());
  }

  /**
   * Test: local docker strategy should use internal container address when it can.
   *
   * @throws Exception
   */
  @Test
  public void localDockerStrategyShouldUseContainerIpAddressWhenAvailable() throws Exception {
    // given
    strategy = new LocalDockerServerEvaluationStrategy(null, null);

    final Map<String, ServerImpl> expectedServers =
        getExpectedServers(CONTAINERINFO_GATEWAY, CONTAINERINFO_IP_ADDRESS, true);

    // when
    final Map<String, ServerImpl> servers =
        strategy.getServers(containerInfo, DEFAULT_HOSTNAME, serverConfs);

    // then
    assertEquals(servers, expectedServers);
  }

  /**
   * Test: local docker strategy should ignore che.docker.ip property.
   *
   * @throws Exception
   */
  @Test
  public void localDockerStrategyShouldIgnoreCheDockerIpProperty() throws Exception {
    // given
    strategy = new LocalDockerServerEvaluationStrategy(CHE_DOCKER_IP, null);

    final Map<String, ServerImpl> expectedServers =
        getExpectedServers(CONTAINERINFO_GATEWAY, CONTAINERINFO_IP_ADDRESS, true);

    // when
    final Map<String, ServerImpl> servers =
        strategy.getServers(containerInfo, DEFAULT_HOSTNAME, serverConfs);

    // then
    assertEquals(servers, expectedServers);
  }

  /**
   * Test: local docker strategy should use passed parameter internalHost when
   * containerInfo.getIpAddress() is null.
   *
   * @throws Exception
   */
  @Test
  public void localDockerStrategyShouldUseProvidedInternalHostWhenContainerInfoUnavailable()
      throws Exception {
    // given
    strategy = new LocalDockerServerEvaluationStrategy(CHE_DOCKER_IP, null);
    when(networkSettings.getIpAddress()).thenReturn("");

    final Map<String, ServerImpl> expectedServers =
        getExpectedServers(CONTAINERINFO_GATEWAY, DEFAULT_HOSTNAME, false);

    // when
    final Map<String, ServerImpl> servers =
        strategy.getServers(containerInfo, DEFAULT_HOSTNAME, serverConfs);

    // then
    assertEquals(servers, expectedServers);
  }

  /**
   * Test: local docker strategy should let che.docker.ip.external take precedence if available
   *
   * @throws Exception
   */
  @Test
  public void localDockerStrategyShouldUseExternalDockerIpPropertyIfAvailable() throws Exception {
    // given
    strategy = new LocalDockerServerEvaluationStrategy(CHE_DOCKER_IP, CHE_DOCKER_IP_EXTERNAL);

    final Map<String, ServerImpl> expectedServers =
        getExpectedServers(CHE_DOCKER_IP_EXTERNAL, CONTAINERINFO_IP_ADDRESS, true);

    // when
    final Map<String, ServerImpl> servers =
        strategy.getServers(containerInfo, DEFAULT_HOSTNAME, serverConfs);

    // then
    assertEquals(servers, expectedServers);
  }

  /**
   * Test: local docker strategy should use containerInfo for externalHost if property is null
   *
   * @throws Exception
   */
  @Test
  public void localDockerStrategyShouldUseContainerInfoForExternalWhenPropertyIsNull()
      throws Exception {
    // given
    strategy = new LocalDockerServerEvaluationStrategy(CHE_DOCKER_IP, null);

    final Map<String, ServerImpl> expectedServers =
        getExpectedServers(CONTAINERINFO_GATEWAY, CONTAINERINFO_IP_ADDRESS, true);

    // when
    final Map<String, ServerImpl> servers =
        strategy.getServers(containerInfo, DEFAULT_HOSTNAME, serverConfs);

    // then
    assertEquals(servers, expectedServers);
  }

  /**
   * Test: local docker strategy should use containerInfo for externalHost if property is null
   *
   * @throws Exception
   */
  @Test
  public void localDockerStrategyShouldUseInternalHostWhenContainerInfoIsUnavailable()
      throws Exception {
    // given
    strategy = new LocalDockerServerEvaluationStrategy(CHE_DOCKER_IP, null);
    when(networkSettings.getGateway()).thenReturn("");

    final Map<String, ServerImpl> expectedServers =
        getExpectedServers(DEFAULT_HOSTNAME, CONTAINERINFO_IP_ADDRESS, true);

    // when
    final Map<String, ServerImpl> servers =
        strategy.getServers(containerInfo, DEFAULT_HOSTNAME, serverConfs);

    // then
    assertEquals(servers, expectedServers);
  }

  private Map<String, ServerImpl> getExpectedServers(
      String externalAddress, String internalAddress, boolean useExposedPorts) {
    String port1;
    String port2;
    if (useExposedPorts) {
      port1 = ":4301";
      port2 = ":4305";
    } else {
      port1 = ":32100";
      port2 = ":32103";
    }
    Map<String, ServerImpl> expectedServers = new HashMap<>();
    expectedServers.put(
        "4301/tcp",
        new ServerImpl(
            "sysServer1-tcp",
            "http",
            externalAddress + ":32100",
            "http://" + externalAddress + ":32100/some/path1",
            new ServerPropertiesImpl(
                "/some/path1",
                internalAddress + port1,
                "http://" + internalAddress + port1 + "/some/path1")));
    expectedServers.put(
        "4305/udp",
        new ServerImpl(
            "devSysServer1-udp",
            null,
            externalAddress + ":32103",
            null,
            new ServerPropertiesImpl("some/path4", internalAddress + port2, null)));
    return expectedServers;
  }
}
