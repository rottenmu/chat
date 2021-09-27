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

public class OneToOneMessage implements Message{
    public static final String TYPE = RequestType.CHAT_SEND_TO_ONE_REQUEST.name();

    private String fromUser;
    private String toUser;
    private String msgId;
    private String content;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

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

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    @Override
    public String toString() {
        return "ChatSendToOneRequest{" +
                "fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
