package com.open.im.remoting.netty.codec;

import com.open.im.remoting.enums.RequestType;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class HeartbeatResponse implements Message {

    /**
     * 类型 - 心跳响应
     */
    public static final String TYPE = RequestType.HEART_RESPONSE.name();

    @Override
    public String toString() {
        return "HeartbeatResponse{}";
    }
}
