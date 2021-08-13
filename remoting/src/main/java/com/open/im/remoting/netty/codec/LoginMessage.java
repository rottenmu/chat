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

public class LoginMessage implements Message{
    public static final String TYPE = RequestType.LOGIN.name();

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
