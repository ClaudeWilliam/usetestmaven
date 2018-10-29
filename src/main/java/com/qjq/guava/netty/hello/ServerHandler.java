package com.qjq.guava.netty.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {
    //一般关闭连接都在服务器端去做，保证了数据的完整性。同样，如果只是用一次就断开就是短链接，如果多次使用就可以使长连接。tcp默认是长连接
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf= (ByteBuf) msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("get msg from client:"+body);
        String response="Hi Client"+body;
        ChannelFuture f= ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));//发送数据，并返回一个ChannelFuture对象
//        f.addListener(ChannelFutureListener.CLOSE);//服务器端处理数据完成发送成功后，关闭与客户端的连接（server端主动），关闭连接需要ChannelFuture对象
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
