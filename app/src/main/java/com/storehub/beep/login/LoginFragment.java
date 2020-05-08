package com.storehub.beep.login;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.storehub.beep.R;
import com.storehub.beep.databinding.LoginFragmentBinding;
import com.storehub.beepcore.base.BaseFragment;
import com.storehub.beepcore.utils.ToastUtil;

public class LoginFragment extends BaseFragment<AuthTokenViewModel, LoginFragmentBinding> {


    @Override
    protected int onCreate() {
        return R.layout.login_fragment;
    }

    @Override
    protected void initView() {
        dataBinding.mPhoneEt.addTextChangedListener(new TextChange());
        String str = getResources().getString(R.string.policy);
        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                avoidHintColor(widget);
            }

            //去除连接下划线
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.text_dark));
                ds.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan, 41, 57, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD),41, 57, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(clickableSpan, 62, 76, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD),62, 76, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dataBinding.mPolicyTv.setMovementMethod(LinkMovementMethod.getInstance());
        dataBinding.mPolicyTv.setText(spannable);

        dataBinding.mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewModel.requestAuthToken(getActivity(), dataBinding.mPhoneEt.getText().toString(), "", "beep");
            }
        });
    }

    @Override
    protected void initData() {
        //数据请求成功通知
        viewModel.getAuthToken().observe(this, new Observer<AuthTokenResp>() {
            @Override
            public void onChanged(AuthTokenResp authTokenResp) {
                Bundle bundle = new Bundle();
                bundle.putString("PhoneNum", dataBinding.mPhoneEt.getText().toString());
                Navigation.findNavController(dataBinding.mPhoneEt).navigate(R.id.action_login_to_otp, bundle);
            }
        });
    }

    private void avoidHintColor(View view){
        if(view instanceof TextView)
            ((TextView)view).setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    protected AuthTokenViewModel initViewModel() {
        return ViewModelProviders.of(this).get(AuthTokenViewModel.class);
    }

    @Override
    protected void showError(Object obj) {
        ToastUtil.show(obj.toString());
    }

    private class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {
            boolean isFilled = dataBinding.mPhoneEt.getText().length() > 0;
            if (isFilled) {
                dataBinding.mLoginBtn.setEnabled(true);
            } else {
                dataBinding.mLoginBtn.setEnabled(false);
            }
        }
    }
}
