package com.storehub.beep.login;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.storehub.beep.R;
import com.storehub.beep.base.SharedPreferencesKey;
import com.storehub.beep.databinding.OtpFragmentBinding;
import com.storehub.beep.main.MainActivity;
import com.storehub.beepcore.base.BaseFragment;
import com.storehub.beepcore.utils.SharePreUtil;
import com.storehub.beepcore.utils.ToastUtil;
import com.storehub.beepcore.utils.ToolUtils;
import com.storehub.beepcore.view.PinEntryEditText;

public class OtpFragment extends BaseFragment<AuthTokenViewModel, OtpFragmentBinding> {

    private TimeCount mTimer;
    private String mPhoneNum;
    private String mVerifyCode;

    @Override
    protected int onCreate() {
        return R.layout.otp_fragment;
    }

    @Override
    protected void initView() {
        mTimer = new TimeCount(60000, 1000);// 构造CountDownTimer对象
        mTimer.start();
        mPhoneNum = getArguments().getString("PhoneNum");
        dataBinding.titleLayout.mTitleTv.setText(getResources().getString(R.string.mobile_verification));
        dataBinding.mOtpTipTv.setText(getResources().getString(R.string.otp_tip1) + mPhoneNum + getResources().getString(R.string.otp_tip2));
        dataBinding.titleLayout.mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_otp_to_login);
            }
        });
        dataBinding.mCodeEt.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                mVerifyCode = str.toString();
                dataBinding.mContinueBtn.setEnabled(true);
                viewModel.requestAuthToken(getActivity(), mPhoneNum, mVerifyCode, "beep");
            }
        });

        dataBinding.mOtpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimer.start();
                viewModel.requestAuthToken(getActivity(), mPhoneNum, "", "beep");
            }
        });

        dataBinding.mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.requestAuthToken(getActivity(), mPhoneNum, mVerifyCode, "beep");
            }
        });
    }

    @Override
    protected void initData() {
        //数据请求成功通知
        viewModel.getAuthToken().observe(this, new Observer<AuthTokenResp>() {
            @Override
            public void onChanged(AuthTokenResp authTokenResp) {
                if (TextUtils.isEmpty(authTokenResp.getMessage())) {
                    SharePreUtil.putString(getActivity(), SharedPreferencesKey.SHAREDPREFERENCES_ACCESSTOKEN, authTokenResp.getAccessToken());
                    SharePreUtil.putString(getActivity(), SharedPreferencesKey.SHAREDPREFERENCES_REFRESHTOKEN, authTokenResp.getRefreshToken());
                    viewModel.requestSignIn(getActivity(), authTokenResp.getAccessToken(), authTokenResp.getRefreshToken());
                }
            }
        });

        viewModel.signIn().observe(this, new Observer<SignInResp>() {
            @Override
            public void onChanged(SignInResp signInResp) {
                ToolUtils.startActivity(getActivity(), MainActivity.class, null);
            }
        });
    }

    @Override
    protected AuthTokenViewModel initViewModel() {
        return ViewModelProviders.of(this).get(AuthTokenViewModel.class);
    }

    @Override
    protected void showError(Object obj) {
        ToastUtil.show(obj.toString());
    }

    /**
     * 定义一个倒计时内部类
     *
     * @author
     */
    private class TimeCount extends CountDownTimer {

        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (dataBinding.mOtpTv != null) {
                dataBinding.mOtpTv.setClickable(false);
                long second = millisUntilFinished / 1000;
                dataBinding.mOtpTv.setText(getResources().getString(R.string.resend_otp) + " (" + second + ")");
            }
        }

        @Override
        public void onFinish() {
            if (dataBinding.mOtpTv != null) {
                dataBinding.mOtpTv.setText(getResources().getString(R.string.resend_otp));
                dataBinding.mOtpTv.setClickable(true);
            }
        }
    }

}
