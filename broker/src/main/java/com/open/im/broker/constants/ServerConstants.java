package com.open.im.broker.constants;

import io.netty.util.AttributeKey;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class ServerConstants {
    //工作节点的父路径
    public static final String BASE_PATH = "/im/nodes";
    public static final String APPLICATION_NAME = "spring.application.name";
    //工作节点的路径前缀
    public static final String PATH_PREFIX = BASE_PATH + "/seq-";
    public static final String PATH_PREFIX_NO_STRIP =  "seq-";
    //统计用户数的znode
    public static final String COUNTER_PATH = "/im/OnlineCounter";

    public static final AttributeKey<String> CHANNEL_NAME = AttributeKey.valueOf("CHANNEL_NAME");

}
