package com.zzy.mvp1.base;

public abstract class BaseModel<P extends BasePresenter, CONTRACT> {

    protected P mPresenter;

    public BaseModel(P presenter) {
        mPresenter = presenter;
    }

    public abstract CONTRACT getContract();

}
