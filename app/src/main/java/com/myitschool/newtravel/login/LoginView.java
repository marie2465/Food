package com.myitschool.newtravel.login;

/**
 * Created by op on 24.12.2017.
 */

public interface LoginView
{
    void rememberUserInfo(String id,String name,String num,String mail,String photo);

    void startMainActivity();

    void showError();

    void showLoadingDialog();

    void dismissLoadingDialog();

    void getRunTimePermissions();

    void checkUserSession();

    void openSignUp();

    void openLogin();
}
