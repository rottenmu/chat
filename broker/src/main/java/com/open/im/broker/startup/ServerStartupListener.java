package com.open.im.broker.startup;

import com.open.im.broker.cluster.Instance;
import com.open.im.broker.cluster.ZookeeperDiscovery;
import com.open.im.broker.config.ServerConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@Component
public class ServerStartupListener implements ApplicationListener<WebServerInitializedEvent> {
    @Autowired
    private CuratorFramework curatorFramework;

    @Value("${spring.application.name}")
    private String applicationName;

   @Autowired
   private ServerConfig serverConfig;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            Instance instance = new Instance(serverConfig.getHost(), serverConfig.getTcpPort());
            instance.setId(serverConfig.getBrokerId());
            ServiceInstance<Instance> serviceInstance = ServiceInstance.<Instance>builder()
                    .id(String.valueOf(serverConfig.getBrokerId()))
                    .name(applicationName)
                    .port(serverConfig.getHttpPort())
                    .payload(instance)
                    .enabled(true)
                    .build();
            JsonInstanceSerializer<Instance> serializer = new JsonInstanceSerializer<Instance>(Instance.class);
            ZookeeperDiscovery zookeeperDiscovery = new ZookeeperDiscovery(curatorFramework, serializer,serviceInstance,true);
            zookeeperDiscovery.run();

            Collection<ServiceInstance<Instance>> serviceInstances = zookeeperDiscovery.queryForInstances(applicationName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
