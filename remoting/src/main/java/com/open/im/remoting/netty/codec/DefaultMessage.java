package com.open.im.remoting.netty.codec;

import com.alibaba.fastjson.JSON;
import com.open.im.remoting.enums.RequestType;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class DefaultMessage implements Message{
    private String type;
    private String message;

    public DefaultMessage(){

    }

    public DefaultMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public DefaultMessage(RequestType type, Message  message){
        this.type = type.name();
        this.message = JSON.toJSONString(message);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DefaultMessage{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
