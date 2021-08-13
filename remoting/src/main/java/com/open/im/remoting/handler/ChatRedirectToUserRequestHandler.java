//package com.open.im.remoting.processor;
//
//import com.open.im.remoting.netty.codec.ChatRedirectToUserRequest;
//import io.netty.channel.Channel;
//import org.springframework.stereotype.Component;
//
///**
// * <p>Title: OpenIm</p>
// * <p>Description: OpenIm</p>
// * <p>Copyright: Copyright (c) 2017-2050</p>
// *
// * @author rotten
// * @version 1.0
// */
//
//@Component
//public class ChatRedirectToUserRequestHandler implements MessageHandler<ChatRedirectToUserRequest> {
//
//    @Override
//    public void execute(Channel channel, ChatRedirectToUserRequest message) {
//        System.out.println(String.format("[execute][发送结果：%s]",message));
//    }
//
//    @Override
//    public String getType() {
//        return ChatRedirectToUserRequest.TYPE;
//    }
//}
