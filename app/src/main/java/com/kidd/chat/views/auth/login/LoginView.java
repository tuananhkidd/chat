package com.kidd.chat.views.auth.login;


public interface LoginView {
    void showProgress();
    void hideProgress();
    void showEmailInputError(String message);
    void showPasswordInpuitError(String message);
    void navigateToHomeScreen();
}
