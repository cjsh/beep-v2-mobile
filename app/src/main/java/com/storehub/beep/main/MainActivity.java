package com.storehub.beep.main;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.storehub.beep.R;
import com.storehub.beep.databinding.MainActivityBinding;
import com.storehub.beep.util.StatusBarUtil;
import com.storehub.beepcore.base.BaseNoModelActivity;
import com.storehub.beepcore.utils.ActivityUtil;
import com.storehub.beepcore.utils.ToastUtil;

public class MainActivity extends BaseNoModelActivity<MainActivityBinding> {
    private Long pressTime = 0l;

    @Override
    protected int onCreate() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setStatusBar(this, false, false);
        NavHostFragment mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        initBottomNavigationView(dataBinding.bottomNavView, mNavHostFragment.getNavController());
    }

    @Override
    protected void initData() {

    }

    private void initBottomNavigationView(BottomNavigationView bottomNavigationView, NavController navController) {
        setImageSize(bottomNavigationView, 90, 90);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        StatusBarUtil.setStatusTextColor(false, MainActivity.this);
                        break;
                    case R.id.cashbackFragment:
                        StatusBarUtil.setStatusTextColor(true, MainActivity.this);
                        break;
                    case R.id.orderFragment:
                        StatusBarUtil.setStatusTextColor(true, MainActivity.this);
                        break;
                    case R.id.accountFragment:
                        StatusBarUtil.setStatusTextColor(true, MainActivity.this);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Long time = System.currentTimeMillis();
            if (time - pressTime > 2000) {
                ToastUtil.show(getResources().getString(R.string.exit_app));
                pressTime = time;
            }else {
                ActivityUtil.getInstance().finishAllActivity();
            }
        }
        return false;
    }

    /**
     * 设置图片尺寸
     * @param view
     * @param width
     * @param height
     */
    public static void setImageSize(BottomNavigationView view,int width,int height) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                ImageView imageView = item.findViewById(R.id.icon);
                imageView.getLayoutParams().width = width;
                imageView.getLayoutParams().height = height;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
