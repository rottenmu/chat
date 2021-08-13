package com.open.im.broker;

import com.alibaba.fastjson.JSON;
import com.open.im.commons.spring.SpringContextUtils;
import com.open.im.remoting.enums.RequestType;
import com.open.im.remoting.netty.codec.HeartbeatRequest;
import com.open.im.remoting.netty.codec.LoginMessage;
import com.open.im.remoting.netty.codec.OneToOneMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

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

@SpringBootTest
public class NettyRemoteBServerAppTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoad(){
//        NettyConnectionManager bean2 = SpringContextUtils.getApplicationContext().getBean(NettyConnectionManager.class);
//        MessageHandlerInitBeanProcessor bean1 = applicationContext.getBean(MessageHandlerInitBeanProcessor.class);
//        NettyConnectionManager bean = applicationContext.getBean(NettyConnectionManager.class);
//        System.out.println(bean);
    }

    @Test
    void jsonTest(){
        Map<String,String> param = new HashMap<String, String>();

        param.put("type","AUTH_REQUEST");
        param.put("message","{\"accessToken\":\"zhangsan\"}");
        System.out.println(JSON.toJSONString(param));
    }

    public static void main(String[] args) {
        Map<String,String> param = new HashMap<String, String>();
        param.put("type", "REQUEST");
        OneToOneMessage oneToOneMessage = new OneToOneMessage();
        oneToOneMessage.setContent("hello, I'm "+ System.getProperty("user"));
        oneToOneMessage.setMsgId("123456");
        oneToOneMessage.setFromUser("a");
        oneToOneMessage.setToUser("b");
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setUserId("a");
        param.put("message",JSON.toJSONString(loginMessage));
        System.out.println(JSON.toJSONString(param));
    }

}
