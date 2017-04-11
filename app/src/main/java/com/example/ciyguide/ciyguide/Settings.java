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

import com.facebook.AccessToken;
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


/**
 * Created by Marilyn Florek, 3/23/2017
 *
 * //edits also done by lorenzo hernandez 4-10-17
 */

public class Settings extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View LogOut = findViewById(R.id.logOut);
        LogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.logOut)
        {
            LogOut();
            LoginManager.getInstance().logOut();
            Intent i = new Intent(Settings.this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void LogOut(){
        SharedPreferences pref = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("userId", "");
        edit.commit();
    }

}
