package com.kidd.chat.views.auth.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.kidd.chat.R;
import com.kidd.chat.common.Constants;
import com.kidd.chat.common.custom_view.ClearableEditText;
import com.kidd.chat.common.custom_view.LoadingDialog;
import com.kidd.chat.common.custom_view.PasswordEditText;
import com.kidd.chat.presenters.auth.login.LoginPresenter;
import com.kidd.chat.presenters.auth.login.LoginPresenterImpl;
import com.kidd.chat.views.auth.register.RegisterActivity;
import com.kidd.chat.views.base.activity.BaseActivity;
import com.kidd.chat.views.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {
    private static final int REQUEST_CODE_CREATE_NEW_ACCOUNT = 0;

    @BindView(R.id.edt_email)
    ClearableEditText edtEmail;
    @BindView(R.id.edt_password)
    PasswordEditText edtPassword;
    @BindView(R.id.txt_create_new_account)
    TextView txtCreateNewAccount;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    boolean checklogin=false;
    private LoadingDialog loadingDialog;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        btnSignIn.setOnClickListener(this);
        txtCreateNewAccount.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null ) {
            navigateToHomeScreen();
        }
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenterImpl(this, this);
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
    public void showEmailInputError(String message) {
        edtEmail.setError(message);
    }

    @Override
    public void showPasswordInpuitError(String message) {
        edtPassword.setError(message);
    }

    @Override
    public void navigateToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in: {
                getPresenter().validateEmailAndPassword(edtEmail.getText(), edtPassword.getText());
            }
            break;

            case R.id.txt_create_new_account: {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_NEW_ACCOUNT);
            }
            break;

            default: {
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CREATE_NEW_ACCOUNT: {
                if (resultCode == RESULT_OK) {
                    String email = data.getStringExtra(Constants.KEY_EMAIL);
                    String password = data.getStringExtra(Constants.KEY_PASSWORD);
                    edtEmail.setText(email);
                    edtPassword.setText(password);
                    FirebaseAuth.getInstance().signOut();
                }
            }
            break;

            default: {
                break;
            }
        }
    }
}
