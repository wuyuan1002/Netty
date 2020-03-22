package com.wuyuan.socketdemo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 服务端
 *
 * @author wuyuan
 * @date 2019/6/2
 */
public class Server {
    public static void main(String[] args) throws Exception {
        /*
         * EventLoopGroup:
         * 用来创建事件循环组
         * bossGroup处理连接事件，然后把连接分发给workerGroup处理读写事件
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
    
        try {
            /*
             * ServerBootstrap和Bootstrap:
             * netty提供的用于启动netty的类, 是启动netty的辅助类,
             * 用于简化netty程序地创建工作,将netty相关的属性进行了封装,
             * 更方便地启动netty程序.不用该类也可以创建netty程序,只是比较麻烦而已.
             *
             * 服务端是ServerBootstrap, 客户端是Bootstrap类
             */
            ServerBootstrap sbs = new ServerBootstrap();
        
            /*
             * 传入事件循环组、连接通道的类型(之后通过反射创建channel对象)、
             * 日志处理器、channel初始化器
             *
             * 这里.handler()和.childHandler()的区别是:
             * .handler()是bossGroup上的处理器，而.childHandler()是workerGroup上的处理器
             */
            sbs.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerInitializer());
        
            // 绑定端口号
            ChannelFuture channelFuture = sbs.bind(8090).sync();
            // 绑定关闭监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            /*
             * 优雅关闭
             */
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
