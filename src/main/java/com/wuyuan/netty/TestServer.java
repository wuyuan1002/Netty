package com.wuyuan.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/1
 */
public class TestServer {
    public static void main(String[] args) {
        /*
         * 两个事件循环组(死循环):
         *  bossGroup只是监听客户端连接，监听到后直接交给workerGroup
         *  bossGroup什么都不做，只是监听连接，workerGroup真正进行连接参数的获取和事件的处理和返回结果给客户端的工作
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        /*
         * 用于启动服务端的类
         */
        ServerBootstrap sbs = new ServerBootstrap();
        sbs.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(null);

    }
}
