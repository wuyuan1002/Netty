package com.wuyuan.httpdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 通道处理器 -- 编写各种回调方法
 *
 * SimpleChannelInboundHandler：请求入队处理器，对进来的请求进行处理,有很多回调方法，在发生特定事件时有框架自动调用
 *
 * @author wuyuan
 * @date 2019/6/2
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    
    /**
     * 1-- 该 handler被添加到 handlerPipeline 后被执行
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
     * 4-- 读取客户端请求并返回响应的方法
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("----- 请求来了 channelRead0 --- 方法被调用-----");
        
        // 对客户端请求进行类型判断 -- 确保是HTTP请求才进行处理
        if (msg instanceof HttpRequest) {
            // 获取HTTP请求
            HttpRequest httpRequest = (HttpRequest) msg;
            //得到访问路径，根据访问路径进行不同的操作
            URI uri = new URI(httpRequest.uri());
            if ("/findAllUser".equals(uri.getPath())) {
                //设置返回数据内容和编码格式
                ByteBuf content = Unpooled.copiedBuffer("Hello World findAllUser", CharsetUtil.UTF_8);
                //设置response的各种属性
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));
                //将response返回
                ctx.writeAndFlush(response);
            } else if ("/deleteUser".equals(uri.getPath())) {
                // 执行相应的处理
            } else {
                // 执行相应的处理
            }
        }
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
        /*
         * 把连接关闭掉
         */
        cause.printStackTrace();
        ctx.close();
    }
}
