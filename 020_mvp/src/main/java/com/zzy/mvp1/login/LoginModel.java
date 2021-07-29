package com.zzy.mvp1.login;

import com.zzy.mvp1.base.BaseModel;
import com.zzy.mvp1.bean.UserInfo;

public class LoginModel extends BaseModel<LoginPresenter, LoginContract.Model> {

    public LoginModel(LoginPresenter presenter) {
        super(presenter);
    }

    @Override
    public LoginContract.Model getContract() {
        return new LoginContract.Model() {
            @Override
            public void executeLogin(String name, String pwd) {
                if ("netease".equalsIgnoreCase(name) && "163".equals(pwd)) {
                    mPresenter.getContract().responseResult(new UserInfo("网易", "彭老师"));
                } else {
                    mPresenter.getContract().responseResult(null);
                }
            }
        };
    }


}
