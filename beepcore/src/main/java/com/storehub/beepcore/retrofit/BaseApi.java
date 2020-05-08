package com.storehub.beepcore.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public abstract class BaseApi {

    /**
     * 初始化Retrofit
     */
    public Retrofit initRetrofit(String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        //支持返回Call<String>
        builder.addConverterFactory(ScalarsConverterFactory.create());
        //支持直接格式化json返回Bean对象
        builder.addConverterFactory(GsonConverterFactory.create());
        //支持RxJava
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.baseUrl(baseUrl);
        OkHttpClient client = initClient();
        if (client != null) {
            builder.client(client);
        }
        return builder.build();
    }

    /*
         OKHttp创建
      */
    private OkHttpClient initClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(initLogInterceptor())
                .retryOnConnectionFailure(true)
                .addInterceptor(mTokenInterceptor)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    Interceptor mTokenInterceptor = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
//            if (AppPrefsUtils.getString(SharedPreferencesKey.SHAREDPREFERENCES_TOKEN,"").equals("")) {
//                return chain.proceed(originalRequest);
//            }
            Request authorised = originalRequest.newBuilder()
                    .addHeader("Content_Type","application/json")
                    .addHeader("charset","UTF-8")
                    .build();
            return chain.proceed(authorised);
        }
    };

    /*
     日志拦截器
  */
    private HttpLoggingInterceptor initLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
