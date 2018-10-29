package com.qjq.guava.netty.pack;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.concurrent.TimeUnit;

public class PackClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();//发送数据的线程组
        Bootstrap b = new Bootstrap();
        b.group(group) //启动类中增加线程组
                .channel(NioSocketChannel.class) //指定管道类型
                .handler(
                        new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel socketChannel) throws  Exception{
                                //对管道内数据进行处理通过HandlerAdapter类
                                ByteBuf buf=Unpooled.copiedBuffer("_$".getBytes());
                                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
//                                socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(5));//定长处理分割包
                                socketChannel.pipeline().addLast(new StringDecoder());
                                socketChannel.pipeline().addLast(new ClientPackHandler());
                            }
                        }
                );
        ChannelFuture cf=b.connect("127.0.0.1",8765).sync(); //连接指定的服务器地址，这里是之前我们的设置的

        cf.channel().write(Unpooled.copiedBuffer("hello netty_$".getBytes())); //写入数据,这里面的_$会被剔除掉
        cf.channel().write(Unpooled.copiedBuffer("hello netty1_$".getBytes())); //写入数据
        cf.channel().write(Unpooled.copiedBuffer("hello netty2___$".getBytes())); //写入数据
        cf.channel().write(Unpooled.copiedBuffer("hello netty3_$中间长度_$".getBytes())); //写入数据
        cf.channel().flush();//发送数据
        cf.channel().writeAndFlush("aaaa_$");
        cf.channel().writeAndFlush("bbbb_$");
        cf.channel().writeAndFlush("cccc_$");

        TimeUnit.SECONDS.sleep(10);
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("10S Send message".getBytes())); //写入发送数据

        cf.channel().closeFuture().sync();//主线程阻塞到这里，直至关闭，第一个链接
        System.out.println("client connect1 close");

        group.shutdownGracefully(); //关闭线程组，释放资源
    }
}
