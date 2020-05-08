package com.storehub.beep.login;

import com.storehub.beep.R;
import com.storehub.beep.databinding.LoginActivityBinding;
import com.storehub.beep.util.StatusBarUtil;
import com.storehub.beepcore.base.BaseNoModelActivity;

public class LoginActivity extends BaseNoModelActivity<LoginActivityBinding> {

    @Override
    protected int onCreate() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setStatusBar(this, false, false);
    }

    @Override
    protected void initData() {

    }
}
