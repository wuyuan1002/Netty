package com.wuyuan.httpdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端 -- 处理浏览器请求的 demo
 *
 * 任意目录下打开cmd，输入curl "http://localhost:8088" 访问
 *
 * @author wuyuan
 * @date 2019/6/1
 */
public class HttpServer {
    public static void main(String[] args) throws Exception {
        /*
         * 两个事件循环组(* 死循环 ==>> while(true){} *):
         *  bossGroup处理连接事件，然后把连接分发给workerGroup处理读写事件
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            // 创建netty提供的用于启动服务器的类
            ServerBootstrap sbs = new ServerBootstrap();
            // 传入事件循环组、连接通道的类型(class类 -- 之后通过反射创建channel对象)、channel初始化器
            sbs.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());
            
            // 绑定端口号
            ChannelFuture channelFuture = sbs.bind(8088).sync();
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
