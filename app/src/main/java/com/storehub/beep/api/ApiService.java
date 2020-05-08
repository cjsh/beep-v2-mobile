package com.storehub.beep.api;

import com.storehub.beep.login.SignInReq;
import com.storehub.beep.login.SignInResp;
import com.storehub.beep.login.AuthTokenReq;
import com.storehub.beep.login.AuthTokenResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    /**
     * 获取AuthToken
     */
    @POST("authorize")
    Observable<AuthTokenResp> getAuthToken(@Body AuthTokenReq authTokenReq);

    /**
     * 获取AuthToken
     */
    @POST("api/login")
    Observable<SignInResp> signIn(@Body SignInReq signInReq);
}
