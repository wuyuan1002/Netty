package com.wuyuan.socketdemo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 *
 * @author wuyuan
 * @date 2019/6/2
 */
public class SocketClient {
    public static void main(String[] args) throws Exception {
        // 客户端只需要一个事件循环组就可以
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            // 创建客户端启动类
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new SocketClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8089).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅关闭
            eventLoopGroup.shutdownGracefully();
        }
    }
}
