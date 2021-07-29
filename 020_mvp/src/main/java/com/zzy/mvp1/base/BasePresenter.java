package com.zzy.mvp1.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends BaseView, M extends BaseModel, CONTRACT> {

    protected M mModel;
    private WeakReference<V> mVWeakReference;

    public BasePresenter() {
        mModel = getModel();
    }

    public void bindView(V v) {
        mVWeakReference = new WeakReference<>(v);
    }

    public void unBindView() {
        if (mVWeakReference != null) {
            mVWeakReference.clear();
            mVWeakReference = null;
            System.gc();
        }
    }

    public V getView() {
        if (mVWeakReference != null) return mVWeakReference.get();
        return null;
    }

    public abstract M getModel();

    public abstract CONTRACT getContract();

}
