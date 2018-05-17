package com.kidd.chat.views.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.kidd.chat.R;
import com.kidd.chat.common.Constants;
import com.kidd.chat.common.custom_view.ClearableEditText;
import com.kidd.chat.common.custom_view.LoadingDialog;
import com.kidd.chat.common.custom_view.PasswordEditText;
import com.kidd.chat.presenters.auth.register.RegisterPresenter;
import com.kidd.chat.presenters.auth.register.RegisterPresenterImpl;
import com.kidd.chat.views.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView, View.OnClickListener {
    @BindView(R.id.edt_first_name)
    ClearableEditText edtFirstName;
    @BindView(R.id.edt_last_name)
    ClearableEditText edtLastName;
    @BindView(R.id.edt_email)
    ClearableEditText edtEmail;
    @BindView(R.id.edt_password)
    PasswordEditText edtPassword;
    @BindView(R.id.edt_confirm_password)
    PasswordEditText edtConfirmPassword;
    @BindView(R.id.txt_sign_in)
    TextView txtSignIn;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private LoadingDialog loadingDialog;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_register;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        btnRegister.setOnClickListener(this);
        txtSignIn.setOnClickListener(this);
    }

    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenterImpl(this, this);
    }

    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        loadingDialog.dismiss();
    }

    @Override
    public void showFirstNameInputError(String message) {
        edtFirstName.setError(message);
    }

    @Override
    public void showLastNameInputError(String message) {
        edtLastName.setError(message);
    }

    @Override
    public void showEmailInputError(String message) {
        edtEmail.setError(message);
    }

    @Override
    public void showPasswordInputError(String message) {
        edtPassword.setError(message);
    }

    @Override
    public void showConfirmPasswordInputError(String message) {
        edtConfirmPassword.setError(message);
    }

    @Override
    public void navigateToLoginScreen(String email, String password) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_EMAIL, email);
        intent.putExtra(Constants.KEY_PASSWORD, password);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register: {
                getPresenter().validateRegisterForm(edtFirstName.getText(),
                        edtLastName.getText(),
                        edtEmail.getText(),
                        edtPassword.getText(),
                        edtConfirmPassword.getText());
            }
            break;

            case R.id.txt_sign_in: {
                finish();
            }
            break;

            default: {
                break;
            }
        }
    }
}
