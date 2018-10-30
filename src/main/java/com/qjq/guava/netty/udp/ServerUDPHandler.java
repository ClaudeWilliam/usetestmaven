package com.qjq.guava.netty.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

import java.net.InetSocketAddress;

public class ServerUDPHandler extends ChannelHandlerAdapter {

    //用于返回个客户端的字典数组
    private static final String[] DICTIONARY = {
            "只要功夫深，铁棒磨成针。",
            "旧时王谢堂前燕,飞入寻常百姓家。",
            "洛阳亲友如相问，一片冰心在玉壶。",
            "一寸光阴一寸金，寸金难买寸光阴。",
            "老骥伏枥，志在千里，烈士暮年，壮心不已"};

    //读取数据进行编解码
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 那我们知道了DatagramPacket它包含了发送者和接受者的消息，
         * 通过content()来获取消息内容
         * 通过sender();来获取发送者的消息
         * 通过recipient();来获取接收者的消息。
         */
        DatagramPacket packet = (DatagramPacket) msg;
        String request = packet.content().toString(CharsetUtil.UTF_8);//上面说了，通过content()来获取消息内容
        //打印Client的请求
        System.out.println("print request msg:" + request);
        printClientAddress(packet);
        if ("query".equals(request)) {
            //包装一下返回的数据，UDP是面向报文的，所以好多数据都在报文里要自己解析，也就是在DatagramPacket中获取和封装
            DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(nextQuote(), CharsetUtil.UTF_8), packet.sender());
            //发送数据
            ctx.writeAndFlush(data);
        }
        //接收到消息，处理消息
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
     * 打印客户端相关的IP地址,UDP打印信息和TCP是不一样的，TCP是在ChannelHandlerContext
     * 但是UDP是在DatagramPacket的数据包
     */
    private void printClientAddress(DatagramPacket data) {
        InetSocketAddress inSocket = data.sender();
        String clientIP = inSocket.getAddress().getHostAddress();
        System.out.println("client IP:" + clientIP);
        System.out.println("client Port：" + inSocket.getPort());
    }

    /**
     * 随机返回一个数组字典中
     *
     * @return
     */
    private String nextQuote() {
        //返回0-DICTIONARY.length中的一个整数。
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];//将谚语列表中对应的谚语返回
    }
}
