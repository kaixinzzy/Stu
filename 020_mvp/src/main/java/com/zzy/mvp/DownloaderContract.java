package com.zzy.mvp;

import com.zzy.mvp.model.ImageBean;

// MVP交互契约规定
public interface DownloaderContract {

    interface PV {
        //  V层告诉P层，需要做什么事情
        void requestDownloader(ImageBean imageBean);

        // P层得到M层的结果后返回，再通知V层
        void responseDownloaderResult(boolean isSuccess, ImageBean imageBean);
    }

    interface M {
        // P层告诉M层，需要做什么事情
        void requestDownloader(ImageBean imageBean);
    }

}
