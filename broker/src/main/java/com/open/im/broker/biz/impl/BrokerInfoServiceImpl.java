package com.open.im.broker.biz.impl;

import com.open.im.broker.biz.BrokerInfoService;
import com.open.im.broker.cluster.Instance;
import com.open.im.broker.cluster.ZookeeperDiscovery;
import com.open.im.broker.cluster.balance.RoundRobinBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@Service
public class BrokerInfoServiceImpl implements BrokerInfoService {
    @Autowired
    private ZookeeperDiscovery zookeeperDiscovery;

    @Override
    public Instance getInstance() {
        return RoundRobinBalance.loadbalance(zookeeperDiscovery);
    }
}
