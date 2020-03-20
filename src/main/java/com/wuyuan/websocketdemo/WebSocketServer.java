package com.wuyuan.websocketdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 *  1.Http协议是一种在TCP连接上的协议，是无状态的连接(短连接)，每次只能客户端主动向服务端发送
 *      数据，服务端才能向客户端回复数据，服务端不能主动向客户端发送数据每次数据发送结束后连接
 *      断开，当有重复的连接时都要创建新的连接，耗费资源。每次数据传递都要包含请求头等额外的信
 *      息，但我们关注的只是数据请求体中的数据本身，额外的信息严重占用了网络资源。
 *
 *  2.WebSocket是一种在TCP连接上进行全双工通信的协议，是一种长连接。
 *      为了建立 WebSocket连接，客户端需要先向服务端发送一条HTTP请求，请求中包含请求连接升级
 *      成 WebSocket连接的信息，服务器端解析这些附加的头信息然后产生应答信息返回给客户端，从而
 *      把 HTTP连接改成 WebSocket连接。WebSocket连接是长连接，一旦建立便会持续存在直到客户端
 *      或者服务器端的某一方主动的关闭连接。客户端和服务端可以自由地传递信息，并且不用携带不必
 *      要的额外信息，更加关注有用的数据本身。
 *
 *
 * @author wuyuan
 * @date 2019/6/4
 */
public class WebSocketServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketServerInitializer());
            
            ChannelFuture channelFuture = sbs.bind(8099).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
