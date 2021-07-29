package com.zzy.mvp1.login;

import com.zzy.mvp1.LoginActivity;
import com.zzy.mvp1.base.BasePresenter;
import com.zzy.mvp1.bean.UserInfo;

public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel, LoginContract.Presenter> {


    @Override
    public LoginModel getModel() {
        return new LoginModel(this);
    }

    @Override
    public LoginContract.Presenter getContract() {
        return new LoginContract.Presenter<UserInfo>() {
            @Override
            public void requestLogin(String name, String pwd) {
                mModel.getContract().executeLogin(name, pwd);
            }

            @Override
            public void responseResult(UserInfo userInfo) {
                getView().getContract().handlerResult(userInfo);
            }
        };
    }
}
