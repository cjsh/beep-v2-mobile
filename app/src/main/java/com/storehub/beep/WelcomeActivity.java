package com.storehub.beep;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.storehub.beep.databinding.WelcomeActivityBinding;
import com.storehub.beep.login.LoginActivity;
import com.storehub.beep.util.NetWorkUtils;
import com.storehub.beep.util.StatusBarUtil;
import com.storehub.beepcore.base.BaseNoModelActivity;
import com.storehub.beepcore.utils.ToastUtil;
import com.storehub.beepcore.utils.ToolUtils;

public class WelcomeActivity extends BaseNoModelActivity<WelcomeActivityBinding> {

    @Override
    protected int onCreate() {
        return  R.layout.welcome_activity;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setStatusBar(this, false, false);
        // 网络状态
        if (!NetWorkUtils.isNetWorkAvailable(this)) {
            ToastUtil.show(getResources().getString(R.string.network_error));
        }

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(3000);
        dataBinding.mSplashRl.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gotoMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    private void gotoMainActivity() {
//        if (AppPrefsUtils.getBoolean(SharedPreferencesKey.SHAREDPREFERENCES_ISLOGIN, false)) {//false未登录true登录
//            ToolUtils.startActivity(SplashActivity.this,MainActivity.class,null);
//        } else {
            ToolUtils.startActivity(WelcomeActivity.this, LoginActivity.class,null);
//        }
        finish();
    }
}
