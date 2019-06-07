package com.wuyuan.nettyhttpdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 *
 * 任意目录下打开cmd，输入curl "http://localhost:8088" 访问
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/1
 */
public class HttpServer {
    public static void main(String[] args) throws Exception {
        /*
         * 两个事件循环组(* 死循环 ==>> while(true){} *):
         *
         *  bossGroup只是监听客户端连接，监听到后直接交给workerGroup
         *  bossGroup什么都不做，只是监听连接，workerGroup真正进行连接参数的获取和事件的处理和返回结果给客户端的工作
         *
         *  bossGroup获取连接，然后把连接分发给workerGroup
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            /*
             * 用于启动服务端
             */
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());

            ChannelFuture channelFuture = sbs.bind(8088).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            /*
             * 关闭
             */
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
