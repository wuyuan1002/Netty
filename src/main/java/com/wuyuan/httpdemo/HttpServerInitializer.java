package com.wuyuan.httpdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器 -- 在管道中添加各种 handler
 *
 * channel通道创建好后自动执行初始化器，初始化通道处理器
 *
 * @author wuyuan
 * @date 2019/6/1
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    
    /**
     *连接被注册之后立刻执行该方法
     */
    @Override
    protected void initChannel(SocketChannel ch) {
        // 获取处理器管道 -- 在管道中添加各种handler
        ChannelPipeline pipeline = ch.pipeline();
        
        //传递netty的Handler -- 完成请求和返回的编解码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //传递自己定义的Handler -- 自定义的处理请求的handler
        pipeline.addLast("HttpServerHandler", new HttpServerHandler());
    }
}
