package com.wuyuan.socketdemo.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 初始化器
 * channel通道创建好后自动执行初始化器，初始化通道处理器
 *
 * @author wuyuan
 * @date 2019/6/2
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        // 获取放处理器的管道
        ChannelPipeline pipeline = ch.pipeline();
        
        // 传递netty的Handler -- 二进制解码器、二进制编码器、字符串解码器、字符串编码器
        pipeline.addLast("LengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast("LengthFieldPrepender", new LengthFieldPrepender(4));
        pipeline.addLast("StringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("StringEncoder", new StringEncoder(CharsetUtil.UTF_8));
        
        // 传递一个自己定义的Handler -- 自己的处理器可以传递多个
        pipeline.addLast("SocketServerHandler", new ServerInboundHandler());
        
    }
}
