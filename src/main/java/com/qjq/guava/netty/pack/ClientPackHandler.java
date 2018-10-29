package com.qjq.guava.netty.pack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientPackHandler extends ChannelHandlerAdapter {

    //读取channel中数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
//            ByteBuf buf = (ByteBuf) msg;
//            byte[] req = new byte[buf.readableBytes()];
//            buf.readBytes(req);
//            String body = new String(req, "UTF-8");
            System.out.println("get string message from server:" + msg);  //处理返回的后的值
        } finally {
            ReferenceCountUtil.release(msg); //unsafe申请的内存
        }

    }

    //对异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
