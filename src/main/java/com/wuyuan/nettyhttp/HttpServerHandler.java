package com.wuyuan.nettyhttp;

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
 * 通道处理器
 *
 * 定义返回数据，把数据返回给客户端
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/2
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {
            System.out.println("----- 请求来了 messageReceived --- 方法被调用-----");
            HttpRequest httpRequest = (HttpRequest) msg;


            System.out.println("请求方法名：" + httpRequest.method().name());

            //得到访问路径，根据访问路径进行不同的操作
            URI uri = new URI(httpRequest.uri());
            if ("/findAllUser".equals(uri.getPath())) {

                System.out.println("请求访问了findAllUser");

                //设置返回数据内容
                ByteBuf content = Unpooled.copiedBuffer("Hello World findAllUser", CharsetUtil.UTF_8);
                //设置response的各种属性
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));

                //将response返回
                ctx.writeAndFlush(response);

            } else if ("/deleteUser".equals(uri.getPath())) {

                System.out.println("请求访问了deleteUser");

                //设置返回内容
                ByteBuf content = Unpooled.copiedBuffer("Hello World deleteUser", CharsetUtil.UTF_8);
                //设置response的各种属性
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));

                //将response返回
                ctx.writeAndFlush(response);

            } else if ("/updateUser".equals(uri.getPath())) {

                System.out.println("请求访问了updateUser");

                //设置返回内容
                ByteBuf content = Unpooled.copiedBuffer("Hello World updateUser", CharsetUtil.UTF_8);
                //设置response的各种属性
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));

                //将response返回
                ctx.writeAndFlush(response);

            } else {
                //设置返回内容
                ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
                //设置response的各种属性
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));

                //将response返回
                ctx.writeAndFlush(response);

            }

            System.out.println("-----------------------------------------");


        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active --- 方法被调用");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered --- 方法被调用");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added --- 方法被调用");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive --- 方法被调用");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered --- 方法被调用");
        super.channelUnregistered(ctx);
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
