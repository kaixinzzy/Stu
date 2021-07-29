package com.zzy.netty;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// https://blog.csdn.net/qq_25430563/article/details/109728121
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        NettyChatClient.getInstance().sendMessage(cmdJson.toJSONString());
    }

    private void initSocket() {
//        NettyChatClient nettyChatClient = NettyChatClient.newInstance(Constant.SERVERIP, Constant.PORT);
//        nettyChatClient.init(new INettyMessageListener() {
//            @Override
//            public void onReceive(String message) {
//                for (INettyMessageListener nettyMessageListener : mIMessageListenerList) {
//                    nettyMessageListener.onReceive(message);
//                }
//            }
//
//            @Override
//            public void onConnectSuccess() {
//                for (INettyMessageListener nettyMessageListener : mIMessageListenerList) {
//                    nettyMessageListener.onConnectSuccess();
//                }
//            }
//
//            @Override
//            public void onError() {
//                for (INettyMessageListener nettyMessageListener : mIMessageListenerList) {
//                    nettyMessageListener.onError();
//                }
//            }
//        });
//        nettyChatClient.connect();
    }


}