package com.storehub.beepcore.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleObserver;

import com.storehub.beepcore.lifecycle.BaseViewModel;

public abstract class BaseLazyFragment<VM extends BaseViewModel, DB extends ViewDataBinding>
        extends BaseFragment<VM, DB> implements LifecycleObserver {

    private boolean visibleToUser;

    @Override
    public void onResume() {
        super.onResume();
        if (!visibleToUser) {
            visibleToUser = true;
            lazyLoad();
        }
    }

    /**
     * 懒加载，只有在Fragment第一次创建且第一次对用户可见
     */
    protected abstract void lazyLoad();
}
