package com.kidd.chat.presenters.auth.login;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.kidd.chat.R;
import com.kidd.chat.common.utils.ToastUtils;
import com.kidd.chat.common.utils.UserAuth;
import com.kidd.chat.views.auth.login.LoginView;


/**
 * Created by TranThanhTung on 19/02/2018.
 */

public class LoginPresenterImpl implements LoginPresenter {
    public static final String TAG = "LoginPresenterImpl";

    private Context context;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onViewDestroy() {
        context = null;
        loginInteractor.onViewDestroy();
    }

    @Override
    public void validateEmailAndPassword(String email, String password) {
        if (email.isEmpty()) {
            loginView.showEmailInputError(context.getString(R.string.enter_email));
            return;
        }

        final String trimEmail = email.trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(trimEmail).matches()) {
            loginView.showEmailInputError(context.getString(R.string.invalid_email));
            return;
        }

        if (password.isEmpty()) {
            loginView.showPasswordInpuitError(context.getString(R.string.enter_password));
            return;
        }
        loginView.showProgress();
        loginInteractor.login(trimEmail, password, new OnLoginCompleteListener() {
            @Override
            public void onLoginSuccess() {
                loginView.hideProgress();
                UserAuth.saveLoginState(context, trimEmail);
                loginView.navigateToHomeScreen();
            }

            @Override
            public void onWrongEmailOrPassword() {
                loginView.hideProgress();
                ToastUtils.quickToast(context, R.string.wrong_email_or_password);
            }

            @Override
            public void onError(String message) {
                Log.i(TAG, "onError: " + message);
                loginView.hideProgress();
                ToastUtils.quickToast(context, R.string.unexpected_error_occurred);
            }
        });

    }

   
}
