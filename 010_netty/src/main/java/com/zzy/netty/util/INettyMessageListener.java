package com.zzy.netty.util;

public interface INettyMessageListener {

    void onReceive(String message);

    void onConnectSuccess();

    void onError();

}
