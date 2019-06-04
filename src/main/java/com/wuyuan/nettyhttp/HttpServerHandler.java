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
 * ͨ��������
 *
 * ���巵�����ݣ������ݷ��ظ��ͻ���
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/2
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {
            System.out.println("----- �������� messageReceived --- ����������-----");
            HttpRequest httpRequest = (HttpRequest) msg;


            System.out.println("���󷽷�����" + httpRequest.method().name());

            //�õ�����·�������ݷ���·�����в�ͬ�Ĳ���
            URI uri = new URI(httpRequest.uri());
            if ("/findAllUser".equals(uri.getPath())) {

                System.out.println("���������findAllUser");

                //���÷�����������
                ByteBuf content = Unpooled.copiedBuffer("Hello World findAllUser", CharsetUtil.UTF_8);
                //����response�ĸ�������
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));

                //��response����
                ctx.writeAndFlush(response);

            } else if ("/deleteUser".equals(uri.getPath())) {

                System.out.println("���������deleteUser");

                //���÷�������
                ByteBuf content = Unpooled.copiedBuffer("Hello World deleteUser", CharsetUtil.UTF_8);
                //����response�ĸ�������
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));

                //��response����
                ctx.writeAndFlush(response);

            } else if ("/updateUser".equals(uri.getPath())) {

                System.out.println("���������updateUser");

                //���÷�������
                ByteBuf content = Unpooled.copiedBuffer("Hello World updateUser", CharsetUtil.UTF_8);
                //����response�ĸ�������
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));

                //��response����
                ctx.writeAndFlush(response);

            } else {
                //���÷�������
                ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
                //����response�ĸ�������
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.readableBytes()));

                //��response����
                ctx.writeAndFlush(response);

            }

            System.out.println("-----------------------------------------");


        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active --- ����������");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered --- ����������");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added --- ����������");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive --- ����������");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered --- ����������");
        super.channelUnregistered(ctx);
    }

    /**
     * �����쳣���ø÷���
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /*
         * �����ӹرյ�
         */
        cause.printStackTrace();
        ctx.close();
    }
}
