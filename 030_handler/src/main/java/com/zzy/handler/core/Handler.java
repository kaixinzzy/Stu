package com.zzy.handler.core;

public class Handler {

    private Looper mLooper;
    private MessageQueue mMessageQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                        + " that has not called Looper.prepare()");
        }
        mMessageQueue = mLooper.mMessageQueue;
    }

    public void sendMessage(Message message) {
        // 将消息放入队列
        enqueueMessage(message);
    }

    private void enqueueMessage(Message message) {
        // 赋值当前Handler
        message.target = this;
        // 将消息存入队列
        mMessageQueue.enqueueMessage(message);
    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }

    // 给开发者提供的开放API，用于重写和回调
    public void handleMessage(Message msg) {}

}
