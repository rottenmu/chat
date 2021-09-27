package com.open.im.broker.config;

import com.open.im.broker.cluster.ZookeeperConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@Configuration
public class ZookeeperConfig {
    @Autowired
    private Environment environment;

    @Bean
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework() {
        String connectString = environment.getProperty(ZookeeperConstant.ZOOKEEPER_CONNECT_STRING);
        if (StringUtils.isEmpty(connectString)) {
            throw new IllegalArgumentException(ZookeeperConstant.ZOOKEEPER_CONNECT_STRING + " can't be null or empty");
        }

        int retryCount = environment.getProperty(ZookeeperConstant.ZOOKEEPER_RETRY_COUNT, Integer.class);
        int sleepTime =  environment.getProperty(ZookeeperConstant.ZOOKEEPER_SLEEP_TIME, Integer.class);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(connectString).retryPolicy(new ExponentialBackoffRetry(sleepTime, retryCount)).build();
        curatorFramework.start();

        return curatorFramework;
    }
}
