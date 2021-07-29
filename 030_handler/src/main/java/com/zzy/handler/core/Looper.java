package com.zzy.handler.core;

public class Looper {

    public MessageQueue mMessageQueue;
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();

    private Looper() {
        mMessageQueue = new MessageQueue();
    }

    public static void prepare() {
        // 主线程只有唯一一个Looper对象
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }

        // 应用启动时，初始化赋值
        sThreadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    // 轮询提取消息
    public static void loop() {
        Looper me = myLooper();
        final MessageQueue queue = me.mMessageQueue;
        while (true) {
            Message nextMessage = queue.next();
            if (nextMessage != null) {
                if (nextMessage.target != null) {
                    // 处理消息
                    nextMessage.target.dispatchMessage(nextMessage);
                }
            }
        }
    }

}
