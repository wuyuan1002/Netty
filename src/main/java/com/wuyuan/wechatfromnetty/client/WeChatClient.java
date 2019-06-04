package com.wuyuan.wechatfromnetty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/4
 */
public class WeChatClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new WeChatClientInitializer());

            Channel channel = bootstrap.connect("localhost", 8090).sync().channel();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            Scanner scanner = new Scanner(System.in);

            while (true) {
                channel.writeAndFlush(br.readLine() + "\r\n");
            }

        } finally {
            //优雅关闭
            eventLoopGroup.shutdownGracefully();
        }
    }
}
