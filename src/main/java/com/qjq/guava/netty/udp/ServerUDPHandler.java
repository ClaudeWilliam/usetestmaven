package com.qjq.guava.netty.udp;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class ServerUDPHandler extends ChannelHandlerAdapter {

    //读取数据进行编解码
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DatagramPacket packet = (DatagramPacket) msg;
        String req = packet.content().toString(CharsetUtil.UTF_8);//上面说了，通过content()来获取消息内容
        System.out.println(req);
        //打印客户端的IP和地址 UDP
//        printClientAdress(ctx);
    }

    //发生异常后的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("netty 发生异常");
        cause.printStackTrace();
    }

    //当channel激活的时候要做的事情
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("netty 连接激活");
    }

    //当连接断开的时候操作
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("netty 失去连接");
    }

    /**
     * 打印客户端相关的IP地址,UDP打印IP地址错误
     */
    private void printClientAdress(ChannelHandlerContext ctx){
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostName();
        System.out.println("clientIP:"+clientIP);
        System.out.println(inSocket.getPort());
    }
}
