package com.zzy.mvp1.login;

import com.zzy.mvp1.bean.BaseEntity;

public interface LoginContract {

    interface Model {
        void executeLogin(String name, String pwd);
    }

    interface Presenter<T extends BaseEntity> {
        void requestLogin(String name, String pwd);
        void responseResult(T t);
    }

    interface View<T extends BaseEntity> {
        void handlerResult(T t);
    }

}
