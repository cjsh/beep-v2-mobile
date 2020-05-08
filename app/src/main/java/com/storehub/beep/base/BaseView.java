package com.storehub.beep.base;

/**
 * View基类
 */
public interface BaseView {

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void hideLoading();

    /**
     * 显示异常提示
     */
    void showError(String msg);

}
