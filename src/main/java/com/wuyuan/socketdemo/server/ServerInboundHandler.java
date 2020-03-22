package com.wuyuan.socketdemo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 通道处理器:
 * 这个是入栈处理器, 在事件进来时进行处理, 回调方法按照以下顺序调用
 *
 * @author wuyuan
 * @date 2019/6/2
 */
public class ServerInboundHandler extends SimpleChannelInboundHandler<String> {
    
    /**
     * 1-- 连接被添加时执行
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added --- 方法被调用");
        super.handlerAdded(ctx);
    }
    
    /**
     * 2-- 连接被注册时执行
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered --- 方法被调用");
        super.channelRegistered(ctx);
    }
    
    /**
     * 3-- 连接处于活动状态被执行
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active --- 方法被调用");
        super.channelActive(ctx);
    }
    
    /**
     * 4-- 接受到客户端的数据后处理信息并向客户端返回一些数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Thread.sleep(500);
        // 接收客户端消息并进行处理
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);
        // 向客户端返回信息
        ctx.channel().writeAndFlush("*** from server : " + UUID.randomUUID() + " ***");
    }
    
    /**
     * 5-- 连接处于不活动状态时执行
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive --- 方法被调用");
        super.channelInactive(ctx);
    }
    
    /**
     * 6-- 连接解除注册时被执行
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered --- 方法被调用");
        super.channelUnregistered(ctx);
    }
    
    /**
     * 7-- 连接移除时被执行
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler removed --- 方法被调用");
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
