package com.open.im.broker;

import com.alibaba.fastjson.JSON;
import com.open.im.broker.cluster.Instance;
import com.open.im.broker.cluster.ZookeeperDiscovery;
import com.open.im.broker.utils.ClientUtils;
import com.open.im.remoting.netty.codec.LoginMessage;
import com.open.im.remoting.netty.codec.OneToOneMessage;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
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

@SpringBootTest(classes = {NettyRemoteAServerApp.class})
public class NettyRemoteBServerAppTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private ZookeeperDiscovery zookeeperDiscovery;

    @Test
    void contextLoad(){
        try {
            Collection<ServiceInstance<Instance>> serviceInstances = zookeeperDiscovery.queryForInstances("netty-server");
            System.out.println(serviceInstances);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        boolean reachable = ClientUtils.isReachable("192.168.58.1", 22002, 30000);
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
