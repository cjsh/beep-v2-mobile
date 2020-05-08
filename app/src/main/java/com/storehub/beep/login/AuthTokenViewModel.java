package com.storehub.beep.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.storehub.beep.R;
import com.storehub.beep.api.Api;
import com.storehub.beepcore.lifecycle.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuthTokenViewModel extends BaseViewModel {
    /**
     * 当数据请求成功回调
     */
    protected MutableLiveData<AuthTokenResp> verifyData = new MutableLiveData<>();
    protected MutableLiveData<SignInResp> signData = new MutableLiveData<>();

    /**
     * 获取验证码
     */
    public void requestAuthToken(final Context context, String username, String password, String client) {
        showDialog.setValue(true, context.getResources().getString(R.string.loading));

        AuthTokenReq authTokenReq = new AuthTokenReq();
        authTokenReq.setGrant_type("otp");
        authTokenReq.setUsername(username);
        authTokenReq.setClient(client);
        authTokenReq.setPassword(password);
        Disposable disposable = Api.getAuthInstance().getAuthToken(authTokenReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthTokenResp>() {
                    @Override
                    public void accept(AuthTokenResp verifyResp) throws Exception {
                        showDialog.setValue(false);
                        verifyData.setValue(verifyResp);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showDialog.setValue(false);
                        /*
                         * 发生了错误，通知UI层
                         */
                        error.setValue(context.getResources().getString(R.string.request_error));
                    }
                });
        addDisposable(disposable);
    }

    public MutableLiveData<AuthTokenResp> getAuthToken() {
        return verifyData;
    }

    /**
     * 登录
     */
    public void requestSignIn(final Context context, String accessToken, String refreshToken) {
        showDialog.setValue(true, context.getResources().getString(R.string.loading));

        SignInReq signInReq = new SignInReq();
        signInReq.setAccessToken(accessToken);
        signInReq.setRefreshToken(refreshToken);
        Disposable disposable = Api.getAuthInstance().signIn(signInReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SignInResp>() {
                    @Override
                    public void accept(SignInResp signInResp) throws Exception {
                        showDialog.setValue(false);
                        signData.setValue(signInResp);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showDialog.setValue(false);
                        /*
                         * 发生了错误，通知UI层
                         */
                        error.setValue(context.getResources().getString(R.string.request_error));
                    }
                });
        addDisposable(disposable);
    }

    public MutableLiveData<SignInResp> signIn() {
        return signData;
    }
}
