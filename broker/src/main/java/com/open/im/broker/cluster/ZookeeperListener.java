package com.open.im.broker.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.DisposableBean;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class ZookeeperListener implements DisposableBean {
    private static final String PATH = "/chat/client";
    private CuratorFramework curatorFramework;

    public ZookeeperListener(CuratorFramework curatorFramework) throws Exception {
    }

    @Override
    public void destroy() throws Exception {
        curatorFramework.close();
    }

    public boolean hasPath(String path) throws Exception {
        return curatorFramework.checkExists().forPath(path) != null;
    }
}
