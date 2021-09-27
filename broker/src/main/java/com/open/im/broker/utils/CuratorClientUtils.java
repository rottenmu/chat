package com.open.im.broker.utils;


import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class CuratorClientUtils {
    private CuratorClientUtils(){}

    public static boolean isNodeExist(CuratorFramework client, String zkPath) {
        try {
            Stat stat = client.checkExists().forPath(zkPath);
            if (null == stat) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
