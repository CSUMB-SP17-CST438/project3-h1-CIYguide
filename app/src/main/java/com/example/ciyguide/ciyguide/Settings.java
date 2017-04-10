package com.example.ciyguide.ciyguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.Arrays;

import com.example.ciyguide.ciyguide.MainActivity;

/**
 * Created by Marilyn Florek, 3/23/2017
 */
 
public class Settings extends AppCompatActivity {

    CallbackManager cbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cbManager = CallbackManager.Factory.create();
        LoginButton login = (LoginButton) findViewById(R.id.login_button);
        login.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        LoginManager.getInstance().registerCallback(cbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback(){
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response){
                                Log.d("SHOW ME THE MONEY", response.toString());
                                try{
                                    Log.d("value", response.getJSONObject().get("id").toString());
                                }catch(Exception e)
                                {
                                    Log.d("Json object err", "can't access it this way");
                                    Log.d("What you have", response.getJSONObject().toString());
                                }
                            }
                        }
                );



                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        if(!MainActivity.isLoggedIn()){
            LogOut();
            SharedPreferences pref = getSharedPreferences(MainActivity.prefs, Context.MODE_PRIVATE);
            Log.d("Is it gone", pref.getString("userId", ""));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!MainActivity.isLoggedIn()){
            LogOut();
            SharedPreferences pref = getSharedPreferences(MainActivity.prefs, Context.MODE_PRIVATE);
            Log.d("Is it gone", pref.getString("userId", ""));
        }
    }


    public void LogOut(){
        SharedPreferences pref = getSharedPreferences(MainActivity.prefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("userId", "");
        edit.commit();
    }

}
