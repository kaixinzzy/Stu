package com.zzy.netty.util;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

@ChannelHandler.Sharable
public class ReconnectHandler extends SimpleChannelInboundHandler<String> {
    private int retries = 0;
    private RetryPolicy mRetryPolicy;
    private NettyChatClient mNettyChatClient;

    public ReconnectHandler(NettyChatClient nettyChatClient) {
        this.mNettyChatClient = nettyChatClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        LogUtil.e("Successfully established a connection to the server.");
        retries = 0;
        ctx.fireChannelActive();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (retries == 0) {
//            LogUtil.e("Lost the TCP connection with the server.");
            ctx.close();
        }
        boolean allowRetry = getRetryPolicy().allowRetry(retries);
        if (allowRetry) {
            long sleepTimeMs = getRetryPolicy().getSleepTimeMs(retries);
//            LogUtil.e(String.format("Try to reconnect to the server after %dms. Retry count: %d.", sleepTimeMs, ++retries));
            final EventLoop eventLoop = ctx.channel().eventLoop();
            eventLoop.schedule(() -> {
                System.out.println("Reconnecting ...");
                mNettyChatClient.connect();
            }, sleepTimeMs, TimeUnit.MILLISECONDS);
        }
        ctx.fireChannelInactive();
    }

    /**
     * 心跳监测
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    ctx.close();
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
                default:
            }
//            LogUtil.e(ctx.channel().remoteAddress() + " " + eventType);
        }
    }

    /**
     * 收到消息后调用
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String message) {
//        LogUtil.e("======================");
//        LogUtil.e("收到消息" + message);
    }

    private RetryPolicy getRetryPolicy() {
        if (this.mRetryPolicy == null) {
            this.mRetryPolicy = mNettyChatClient.getRetryPolicy();
        }
        return this.mRetryPolicy;
    }
}
