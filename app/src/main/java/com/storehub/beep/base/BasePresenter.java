package com.storehub.beep.base;

import android.content.Context;

import com.storehub.beep.util.NetWorkUtils;

/**
 * Presenter基类
 */
public class BasePresenter{

    public BaseView mBaseView;

    /*
          检查网络是否可用
       */
    public boolean checkNetWork(Context context){
        if(NetWorkUtils.isNetWorkAvailable(context)){
            return true;
        }
        mBaseView.showError("网络不可用");
        return false;
    }
}
