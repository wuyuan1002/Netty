package com.wuyuan.nettysocketdemo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * 通道处理器
 *
 * 定义返回数据，把数据返回给客户端
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/2
 */
public class SocketClientHandler extends SimpleChannelInboundHandler<String> {
    
    /**
     * 当连接活动状态后主动向服务器端发送一个消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("来自客户端的问候！");
    }
    
    /**
     * 接收到服务器端消息后回调 -- 输出信息并返回给服务器端一些消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Thread.sleep(500);
        // 接收信息并输出
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);
        // 向服务器端返回信息
        ctx.channel().writeAndFlush("### client output : " + LocalDateTime.now() + " ###");
    }
    
    /**
     * 发生异常调用该方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 把连接关闭掉
        cause.printStackTrace();
        ctx.close();
    }
}
