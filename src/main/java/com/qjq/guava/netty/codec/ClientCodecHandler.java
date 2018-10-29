package com.qjq.guava.netty.codec;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientCodecHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Response response = (Response) msg;
            System.out.println("this from server responseId：" + response.getResponseId() + "-time:" + response.getTime() + "-message:" + response.getMessage());


        } finally {
            ReferenceCountUtil.release(msg); //unsafe申请的内存
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            Request req = new Request();
            req.setRequestId(String.valueOf(i));
            req.setMessage("Hello Word" + i);
            req.setHost("192.0.0." + i);
            ctx.writeAndFlush(req);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
