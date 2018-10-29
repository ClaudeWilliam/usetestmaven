package com.qjq.guava.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup pGroup=new NioEventLoopGroup();//用于处理服务器接收客户的连接,ParentGroup
        EventLoopGroup cGroup=new NioEventLoopGroup();//进行网络读写的,进行业务操作
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(pGroup,cGroup)//绑定两个线程组
                .channel(NioServerSocketChannel.class)//指定NIO的模式,服务器端模式（Socket通信），也可以是Client或者UDP
                .option(ChannelOption.SO_BACKLOG,1024) //TCP缓冲区
                .option(ChannelOption.SO_SNDBUF,32*1024) //发送区缓存
                .option(ChannelOption.SO_RCVBUF,32*1024) //接收区缓存
                .option(ChannelOption.SO_KEEPALIVE,true)
                .childHandler( new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws  Exception{
                        //对管道内数据进行处理通过HandlerAdapter类
                        socketChannel.pipeline().addLast(new ServerHandler());
                    }
                }
                );
        ChannelFuture cf= bootstrap.bind(8765).sync();//绑定接口，同步的
//        ChannelFuture cf1= bootstrap.bind(8764).sync();//绑定2个接口，同一个启动器对象

        cf.channel().closeFuture().sync(); //等待关闭,主线程阻塞到这里
        System.out.println("server connect1 close");

//        cf1.channel().closeFuture().sync();//同步等待 8764端口
//        System.out.println("server connect2 close"); //打印关闭信息，但是这两个链接是不是相互阻塞的？？

        pGroup.shutdownGracefully(); //关闭parent线程组，资源释放
        cGroup.shutdownGracefully(); //关闭child线程组，资源释放
    }
}
