package com.qjq.guava.netty.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class ClientUDPHandler extends ChannelHandlerAdapter {

    //读取数据进行编解码
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    //发生异常后的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("netty 出现异常");
        cause.printStackTrace();
    }

    //当channel激活的时候要做的事情
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("netty 激活");
        String data = "谚语字典查询？";
        //UDP 要在数据中封装了IP地址和端口号,DatagramPacket
        InetSocketAddress insocket = new InetSocketAddress("127.0.0.1", 1234);
        DatagramPacket packet = new DatagramPacket(Unpooled.copiedBuffer(data, CharsetUtil.UTF_8), insocket);
        ctx.writeAndFlush(packet);
    }

    //当连接断开的时候操作
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("netty 连接断开");
    }
}
