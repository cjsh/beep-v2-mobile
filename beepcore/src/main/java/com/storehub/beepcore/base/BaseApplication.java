package com.storehub.beepcore.base;

import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.multidex.MultiDexApplication;

/**
 * Created by Administrator on 2018/3/24.
 */

public class BaseApplication extends MultiDexApplication {

    public static BaseApplication myApplication = null;

    public static BaseApplication getInstance() {
        if (myApplication == null) {
            synchronized (BaseApplication.class) {
                if (myApplication == null) {
                    myApplication = new BaseApplication();
                }
            }
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        // 加载系统默认设置，字体不随用户设置变化
        Resources res = super.getResources();
        Configuration configs = new Configuration();
        configs.setToDefaults();
        res.updateConfiguration(configs, res.getDisplayMetrics());
    }
}
