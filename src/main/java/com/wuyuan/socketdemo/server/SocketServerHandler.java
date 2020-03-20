package com.wuyuan.socketdemo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 通道处理器
 *
 * 定义返回数据，把数据返回给客户端
 *
 * @author wuyuan
 * @date 2019/6/2
 */
public class SocketServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 接受到客户端的数据后处理信息并向客户端返回一些数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Thread.sleep(500);
        // 接收信息并输出
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);
        // 向客户端返回信息
        ctx.channel().writeAndFlush("*** from server : " + UUID.randomUUID() + " ***");
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
