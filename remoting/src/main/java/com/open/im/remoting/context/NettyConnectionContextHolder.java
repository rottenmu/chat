//package com.open.im.remoting.context;
//
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
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
//public class NettyConnectionContextHolder {
//    private volatile static  ThreadLocal<NettyConnectionContextHolder> THREAD_LOCAL = new ThreadLocal<NettyConnectionContextHolder>(){
//        @Override
//        protected NettyConnectionContextHolder initialValue() {
//            return new NettyConnectionContextHolder();
//        }
//    };
//
//    private NettyConnectionHolder nettyConnectionHolder;
//
//    public static NettyConnectionContextHolder getContextHolder() {
//        return THREAD_LOCAL.get();
//    }
//
//
//    public static void clean(){
//        NettyConnectionContextHolder nettyConnectionContextHolder = THREAD_LOCAL.get();
//        if (nettyConnectionContextHolder == null) {
//            return;
//        }
//        NettyConnectionHolder nettyConnectionManager = nettyConnectionContextHolder.getNettyConnectionContext();
//       if (nettyConnectionManager == null){
//           THREAD_LOCAL.remove();
//       }
//    }
//
//    public void setNettyConnectionManager(NettyConnectionHolder context){
//        this.nettyConnectionHolder = context;
//    }
//
//    public NettyConnectionHolder getNettyConnectionContext(){
//        return this.nettyConnectionHolder;
//    }
//
//    @Override
//    public int hashCode() {
//        return HashCodeBuilder.reflectionHashCode(this);
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        return EqualsBuilder.reflectionEquals(this, object);
//    }
//
//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
//    }
//}
