package com.zzy.mvp1.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseView<P extends BasePresenter, CONTRACT> extends AppCompatActivity {

    protected P mPresenter;

    public abstract P getPresenter();

    public abstract CONTRACT getContract();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        mPresenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unBindView();
    }
}
