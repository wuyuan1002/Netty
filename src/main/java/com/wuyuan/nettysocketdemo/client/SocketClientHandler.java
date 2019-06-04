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
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + ", " + msg);
        ctx.channel().writeAndFlush("### client output : " + LocalDateTime.now() + " ###");


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("来自客户端的问候！");
    }

    /**
     * 发生异常调用该方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /*
         * 把连接关闭掉
         */
        cause.printStackTrace();
        ctx.close();
    }
}
