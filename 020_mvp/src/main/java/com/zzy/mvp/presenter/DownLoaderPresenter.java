package com.zzy.mvp.presenter;

import com.zzy.mvp.DownloaderContract;
import com.zzy.mvp.MainActivity;
import com.zzy.mvp.engine.DownLoaderEngine;
import com.zzy.mvp.model.ImageBean;

// P层几乎不做事情？谷歌的sample中，P层是包揽了所有的活
public class DownLoaderPresenter implements DownloaderContract.PV {

    private MainActivity view;
    private DownLoaderEngine mModel; // 下载的模型

    public DownLoaderPresenter(MainActivity view) {
        this.view = view;
        mModel = new DownLoaderEngine(this);
    }

    @Override
    public void requestDownloader(ImageBean imageBean) {
        mModel.requestDownloader(imageBean);
    }

    @Override
    public void responseDownloaderResult(final boolean isSuccess, final ImageBean imageBean) {
        view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.responseDownloaderResult(isSuccess, imageBean);
            }
        });
    }
}
