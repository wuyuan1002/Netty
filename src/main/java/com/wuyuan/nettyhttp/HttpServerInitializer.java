package com.wuyuan.nettyhttp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器
 *
 * channel通道创建好后自动执行初始化器，初始化通道处理器
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/1
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {

        ChannelPipeline pipeline = ch.pipeline();

        //传递netty的Handler
        pipeline.addLast("httpServerCodec", new HttpServerCodec());

        //传递自己定义的Handler
        pipeline.addLast("HttpServerHandler", new HttpServerHandler());

    }
}
