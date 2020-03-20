package com.wuyuan.xintiaojiancechaoshijizhi;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author wuyuan
 * @date 2019/6/4
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        
        ChannelPipeline pipeline = ch.pipeline();
        
        //传递netty的Handler
        /*
         * 超过7秒没有收到客户端的数据则产生读空闲事件，超过6秒没有发送给客户端数据则产生写空闲事件
         * 超过8秒既没有收到客户端的数据也没有给客户端发送数据则产生度写空闲事件
         */
        pipeline.addLast("IdleStateHandler", new IdleStateHandler(7, 6, 8, TimeUnit.SECONDS));
        
        //传递自己定义的Handler
        pipeline.addLast("ServerHandler", new ServerHandler());
        
    }
}