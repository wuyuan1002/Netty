package com.wuyuan.wechatfromnetty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author wuyuan
 * @date 2019/6/4
 */
public class WeChatServerHandler extends SimpleChannelInboundHandler<String> {
    
    
    /**
     * 用来存放已经建立好的连接对象 channel
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    
    
    /**
     * 当收到消息时回调函数
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取连接通道
        Channel channel = ctx.channel();
        //循环所有连接，判断如果是自己发的则显示【自己】，如果不是自己发的显示发送人的链接地址
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + " : " + msg + "\r\n");
            } else {
                ch.writeAndFlush("【自己】:" + msg + "\r\n");
            }
        });
    }
    
    /**
     * 当连接建立后回调函数
     *
     * 当有人加入聊天室时，通知聊天室所有已连接的用户有新人加入聊天室
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //获取新的连接通道
        Channel channel = ctx.channel();
        
        //将新的连接的地址广播给已经连接的所有连接，提示有新人加入聊天室
        channelGroup.writeAndFlush("[服务器] -> " + channel.remoteAddress() + "进入聊天室！\r\n");
        
        //将新的连接存放到channelGroup中
        channelGroup.add(channel);
        
    }
    
    /**
     * 当连接断掉后回调函数
     *
     * 当有人退出聊天室时，通知聊天室所有已连接的人有人退出聊天室
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //获取端开的连接通道
        Channel channel = ctx.channel();
        
        //将断掉的连接的地址广播给已经连接的所有连接，提示有人退出聊天室
        channelGroup.writeAndFlush("[服务器] -> " + channel.remoteAddress() + "退出聊天室！\r\n");
        
        /*
         * 将断开的连接从channelGroup中删除
         *
         * 该方法可以不写，因为当连接断掉后，netty会自动查找channelGroup把断掉的连接删掉
         */
//        channelGroup.remove(channel);
    
    }
    
    /**
     * 当连接处于活动状态时回调函数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线！\r\n");
    }
    
    /**
     * 当连接处于不活动状态时回调函数
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线！\r\n");
    }
    
    /**
     * 发生异常调用该方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
