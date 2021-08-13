//package com.open.im.broker.namesrv;
//
//import com.open.im.commons.codec.Message;
//import org.springframework.core.env.Environment;
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
//public class NameSrvHeartbeatRequest implements Message {
//    public static final String TYPE = "NAME_SRV_CLIENT_HEARTBEAT_REQUEST";
//
//    private String host;
//    private Integer port;
//
//    public NameSrvHeartbeatRequest(){}
//
//    public NameSrvHeartbeatRequest(Environment environment){
//       this.host = environment.getProperty("netty.server.host");
//       this.port = environment.getProperty("netty-server.port", Integer.class);
//    }
//
//    public String getHost() {
//        return host;
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public Integer getPort() {
//        return port;
//    }
//
//    public void setPort(Integer port) {
//        this.port = port;
//    }
//
//    @Override
//    public String toString() {
//        return "NameSrvHeartbeatRequest{" +
//                "host='" + host + '\'' +
//                ", port=" + port +
//                '}';
//    }
//}
