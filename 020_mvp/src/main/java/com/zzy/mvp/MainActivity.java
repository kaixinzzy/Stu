package com.zzy.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzy.mvp.model.ImageBean;
import com.zzy.mvp.presenter.DownLoaderPresenter;
import com.zzy.mvp.utils.Constant;

import androidx.appcompat.app.AppCompatActivity;

// MVP 架构封装
public class MainActivity extends AppCompatActivity implements DownloaderContract.PV {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ImageView mImageView;
    private DownLoaderPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.iv);
        mPresenter = new DownLoaderPresenter(this);
    }

    // 点击事件
    public void down(View view) {
        ImageBean imageBean = new ImageBean();
        imageBean.setRequestPath(Constant.IMAGE_PATH);
        requestDownloader(imageBean);
    }

    @Override
    public void requestDownloader(ImageBean imageBean) {
        if (mPresenter != null) mPresenter.requestDownloader(imageBean);
    }

    @Override
    public void responseDownloaderResult(boolean isSuccess, ImageBean imageBean) {
        Toast.makeText(this, isSuccess ? "下载成功" : "下载失败", Toast.LENGTH_SHORT).show();
        if (isSuccess && imageBean.getBitmap() != null) {
            mImageView.setImageBitmap(imageBean.getBitmap());
        }
    }


}