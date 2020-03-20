package com.wuyuan.xintiaojiancechaoshijizhi;

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
 * 目的：心跳超时的检测机制 -- 检测心跳包，如果超时未检测到心跳则做出相应的处理
 *
 * 说明：只编写了服务端，因为客户端和其他的一样，就不再重复写了
 *      这里的端口写的和 wechatfromnetty聊天室 的一样，启动该服务端后再启动聊天室的客户端才可以正常实验
 *
 * 如果客户端没有在指定的时间内向服务端发送数据则产生读空闲事件，
 * 客户端发送数据但服务端没有向客户端发送数据则产生写空闲事件，
 * 如果是没有写或没有读则产生度写空闲事件
 *
 *
 * @author wuyuan
 * @date 2019/6/4
 */
public class Server {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerInitializer());
            
            ChannelFuture channelFuture = sbs.bind(8090).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
