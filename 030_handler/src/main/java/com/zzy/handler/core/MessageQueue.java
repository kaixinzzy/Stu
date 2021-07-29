package com.zzy.handler.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {

    // 阻塞队列
    BlockingQueue<Message> mBlockingQueue = new ArrayBlockingQueue<>(50);

    // 将Message消息对象存入队列中
    public void enqueueMessage(Message message) {
        try {
            mBlockingQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 从消息队列中取出消息
    public Message next() {
        try {
            return mBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
