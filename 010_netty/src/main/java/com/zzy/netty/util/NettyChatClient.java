package com.zzy.netty.util;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyChatClient {
    private static NettyChatClient mNettyChatClient;
    private static String mServerIp;
    private static int mPort;
    private Channel mChannel;
    /**
     * 重连策略
     */
    private RetryPolicy mRetryPolicy;
    private Bootstrap mBootstrap;

    public void init(INettyMessageListener messageListener) {
        mRetryPolicy = new ExponentialBackOffRetry(3000, Integer.MAX_VALUE, 3 * 1000);
        EventLoopGroup group = new NioEventLoopGroup();
        // bootstrap 可重用, 只需在TcpClient实例化的时候初始化即可.
        mBootstrap = new Bootstrap();
        mBootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new ClientHandlersInitializer());

    }

    public static NettyChatClient newInstance(String serverIp, int port) {
        if (mNettyChatClient == null) {
            mNettyChatClient = new NettyChatClient();
            mServerIp = serverIp;
            mPort = port;
        }
        return mNettyChatClient;
    }

    public static NettyChatClient getInstance() {
        return mNettyChatClient;
    }

    /**
     * 向远程TCP服务器请求连接
     */
    public void connect() {
        synchronized (mBootstrap) {
            ChannelFuture future = mBootstrap.connect(mServerIp, mPort);
            future.addListener(getConnectionListener());
            this.mChannel = future.channel();
        }
    }

    private ChannelFutureListener getConnectionListener() {
        return future -> {
            if (!future.isSuccess()) {
                future.channel().pipeline().fireChannelInactive();
            }
        };
    }

    private class ClientHandlersInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
            pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
            pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
            pipeline.addLast(new ReconnectHandler(mNettyChatClient));
        }

    }


    public RetryPolicy getRetryPolicy() {
        return mRetryPolicy;
    }

    public void sendMessage(String content) {
        if (mChannel != null) {
            mChannel.writeAndFlush(content);
        }
    }

}


