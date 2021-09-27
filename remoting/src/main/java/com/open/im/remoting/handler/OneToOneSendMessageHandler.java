package com.open.im.remoting.handler;

import com.open.im.remoting.context.NettyConnectionHolder;
import com.open.im.remoting.enums.RequestType;
import com.open.im.remoting.netty.codec.ChatSendResponse;
import com.open.im.remoting.netty.codec.DefaultMessage;
import com.open.im.remoting.netty.codec.ForwardMessageRequest;
import com.open.im.remoting.netty.codec.OneToOneMessage;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class OneToOneSendMessageHandler implements MessageHandler<OneToOneMessage> {

    @Override
    public void execute(NioSocketChannel channel, OneToOneMessage message) {
        ChatSendResponse chatSendResponse = new ChatSendResponse();
        chatSendResponse.setMessage("b server received");
        chatSendResponse.setMsgId("234567");
        chatSendResponse.setCode("0");
        channel.writeAndFlush(new DefaultMessage(RequestType.RESPONSE,chatSendResponse));
        ForwardMessageRequest forwardMessageRequest = new ForwardMessageRequest();
        forwardMessageRequest.setMsgId(message.getMsgId());
        forwardMessageRequest.setContent(message.getContent());
        NettyConnectionHolder.send(message.getToUser(), new DefaultMessage(RequestType.FORWRD_REQUEST,forwardMessageRequest));
    }

    @Override
    public String getType() {
        return OneToOneMessage.TYPE;
    }
}
