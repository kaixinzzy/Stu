package com.zzy.stu;

import com.zzy.handler.core.Handler;
import com.zzy.handler.core.Looper;
import com.zzy.handler.core.Message;

import org.junit.Test;

public class ActivityThread {

    @Test
    public void main() {

        // 创建全局唯一的主线程Looper、MessageQueue
        Looper.prepare();

        // 模拟Activity中，创建Handler对象
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println(msg.obj.toString());
            }
        };

        // 子线程发送消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.obj = "hello";
                handler.sendMessage(message);
            }
        }).start();

        // 轮询，取出消息
        Looper.loop();
    }

}
