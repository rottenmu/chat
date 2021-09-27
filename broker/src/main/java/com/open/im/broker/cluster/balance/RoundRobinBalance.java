package com.open.im.broker.cluster.balance;

import com.open.im.broker.cluster.Instance;
import com.open.im.broker.cluster.ZookeeperDiscovery;
import com.open.im.broker.constants.ServerConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class RoundRobinBalance {
    private static final Logger logger = LoggerFactory.getLogger(RoundRobinBalance.class.getName());

    private static Integer position = 0;
    private static final ConcurrentMap<Integer, Instance> serverMap = new ConcurrentHashMap<>();

    public static Instance loadbalance(ZookeeperDiscovery discovery) {
        try {
            Collection<ServiceInstance<Instance>> serviceInstances = discovery.queryForInstances(ServerConstants.APPLICATION_NAME);
            if (CollectionUtils.isNotEmpty(serviceInstances)) {
                List<ServiceInstance<Instance>> instances = serviceInstances.stream().collect(Collectors.toList());
                for (int i = 0; i < instances.size(); i++) {
                    serverMap.put(i, instances.get(i).getPayload());
                }
                Collection<Instance> instanceSet = serverMap.values();
                List<Instance> instanceList = instanceSet.stream().collect(Collectors.toList());
                Instance instance = null;
                synchronized (position){
                    if (position > instanceSet.size()){
                        position = 0;
                    }
                    instance = instanceList.get(position);
                    position ++ ;
                }
                return instance;
            }
            logger.warn("ZookeeperDiscovery's instance is empty");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
