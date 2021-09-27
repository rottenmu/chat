package com.open.im.broker.cluster;

import com.open.im.broker.constants.ServerConstants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import org.apache.curator.x.discovery.details.ServiceDiscoveryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class ZookeeperDiscovery extends ServiceDiscoveryImpl<Instance> implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(ZookeeperDiscovery.class.getName());

    private volatile ServiceInstance<Instance> serviceInstance ;
    private final String basePath = ServerConstants.BASE_PATH;

    public ZookeeperDiscovery(CuratorFramework client, InstanceSerializer<Instance> serializer, ServiceInstance<Instance> serviceInstance, boolean watchInstances){
        super(client, ServerConstants.BASE_PATH, serializer, serviceInstance, watchInstances);
       this.serviceInstance = serviceInstance;
    }

    @Override
    public void run() {
        try {
            registerService(serviceInstance);
            logger.info("Registry zookeeper success, path={}, id={}", basePath, serviceInstance.getPayload().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getId(String path) {
        String sid = null;
        if (null == path) {
            throw new IllegalArgumentException("node path is error");
        }
        int index = path.lastIndexOf(ServerConstants.PATH_PREFIX);
        if (index >= 0) {
            index += ServerConstants.PATH_PREFIX.length();
            sid = index <= path.length() ? path.substring(index) : null;
        }

        if (null == sid) {
            throw new IllegalArgumentException("节点ID获取失败");
        }

        return Long.parseLong(sid);
    }
}
