package com.zzy.mvp.engine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zzy.mvp.DownloaderContract;
import com.zzy.mvp.model.ImageBean;
import com.zzy.mvp.presenter.DownLoaderPresenter;
import com.zzy.mvp.utils.Constant;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoaderEngine implements DownloaderContract.M {

    private DownLoaderPresenter mPresenter;

    public DownLoaderEngine(DownLoaderPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void requestDownloader(ImageBean imageBean) {
        new Thread(new DownLoader(imageBean)).start();
    }

    final class DownLoader implements Runnable {

        private final ImageBean imageBean;

        public DownLoader(ImageBean imageBean) {
            this.imageBean = imageBean;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(imageBean.getRequestPath());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    showUi(Constant.SUCCESS, bitmap);
                } else {
                    showUi(Constant.ERROR, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showUi(Constant.ERROR, null);
            }
        }

        private void showUi(int resultCode, Bitmap bitmap) {
            imageBean.setBitmap(bitmap);
            mPresenter.responseDownloaderResult(resultCode == Constant.SUCCESS, imageBean);
        }
    }
}
