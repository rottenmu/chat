package com.open.im.remoting.netty.codec;

import com.open.im.remoting.enums.RequestType;
import com.open.im.remoting.handler.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class ForwardMessageRequest implements Message{
    public static final String TYPE = RequestType.FORWRD_REQUEST.name();

    private String msgId;
    private String content;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChatRedirectToUserRequest{" +
                "msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
