package com.qjq.guava.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.UUID;

public class ServerCodecHandler extends ChannelHandlerAdapter {
    //一般关闭连接都在服务器端去做，保证了数据的完整性。同样，如果只是用一次就断开就是短链接，如果多次使用就可以使长连接。tcp默认是长连接
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Request request= (Request) msg;
            System.out.println("request from client Host:"+request.getHost()+"-id:"+request.getRequestId()+"-message:"+request.getMessage());
            printClientAdress(ctx);//打印客户端IP地址
            Response response=new Response();
            response.setTime(new Date());
            response.setResponseId(UUID.randomUUID().toString());
            response.setMessage("this from server reply"+request.getRequestId());
            ctx.writeAndFlush(response);//发送数据，并返回一个ChannelFuture对象
        }finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    /**
     * 打印客户端相关的IP地址
     */
    private void printClientAdress(ChannelHandlerContext ctx){
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostName();
        System.out.println("clientIP:"+clientIP);
        System.out.println(inSocket.getPort());
    }
}
