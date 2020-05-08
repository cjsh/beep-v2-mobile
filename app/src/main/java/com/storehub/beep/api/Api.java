package com.storehub.beep.api;


import com.storehub.beep.base.BaseConstant;
import com.storehub.beepcore.retrofit.BaseApi;

import okhttp3.OkHttpClient;

public class Api extends BaseApi {

    /**
     * 静态内部类单例
     */
    private static class ApiHolder {
        private static Api api = new Api();
        private final static ApiService authService = api.initRetrofit(BaseConstant.AUTH_SERVER_URL)
                .create(ApiService.class);
        private final static ApiService baseService = api.initRetrofit(BaseConstant.BASE_SERVER_URL)
                .create(ApiService.class);
    }

    public static ApiService getAuthInstance() {
        return ApiHolder.authService;
    }

    public static ApiService getBaseInstance() {
        return ApiHolder.baseService;
    }

}
