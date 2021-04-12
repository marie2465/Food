package com.myitschool.newtravel.login;

import android.net.MailTo;
import android.os.Handler;
import android.provider.ContactsContract;

import Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by op on 24.12.2017.
 */

public class LoginPresenter
{
    private LoginView view;
    public void bind(LoginView view){
        this.view = view;
    }

    public void unbind(){
        view = null;
    }

    public void signUp(){
        view.openSignUp();
    }

    public void ok_signUp(final String Name, final String Num, final String Mail, String Pass, final Handler mHandler)
    {
        view.showLoadingDialog();
        String uri = Constants.apilink + "users/signup.php?name=" + Name + "&contact=" + Num +
                                          "&e-mail=" +Mail + "&password=" + Pass;

        OkHttpClient client = new OkHttpClient();
        //Execute request
        final Request request = new Request.Builder()
                .url(uri)
                .build();
        //Setup callback
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.showError();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String res = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ob = new JSONObject(res);
                            String id = ob.getString("id");
                            String photo=ob.getString("photo");
                            view.rememberUserInfo(id,Name,Num,Mail,photo);
                            view.startMainActivity();
                            view.dismissLoadingDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            view.showError();
                        }
                    }
                });
            }
        });
    }

    public void login()
    {
        view.openLogin();
    }

    public void ok_login(final String Mail, String Pass, final Handler mHandler)
    {
        view.showLoadingDialog();

        String uri = Constants.apilink + "users/login.php?e-mail=" + Mail + "&password=" + Pass;
        //Set up client
        OkHttpClient client = new OkHttpClient();
        //Execute request
        Request request = new Request.Builder()
                .url(uri)
                .build();
        //Setup callback
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.showError();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                mHandler.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      try {
                                          JSONObject ob = new JSONObject(res);
                                          Boolean success = ob.getBoolean("success");
                                          if (success) {
                                              JSONObject o = ob.getJSONObject("user_id");
                                              String id = o.getString("id");
                                              String name = o.getString("name");
                                              String num=o.getString("number");
                                              String photo=o.getString("photo");
                                              view.rememberUserInfo(id,name,num,Mail,photo);
                                              view.startMainActivity();
                                              view.dismissLoadingDialog();
                                          } else {
                                              view.showError();
                                          }
                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }
                                  }
                              }
                );
            }
        });
    }
}
