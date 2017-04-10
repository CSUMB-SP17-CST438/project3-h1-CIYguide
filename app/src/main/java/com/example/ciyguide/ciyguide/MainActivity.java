package com.example.ciyguide.ciyguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends FragmentActivity {

    public static final String prefs = "MyPrefs";
    CallbackManager cbManager;
    public SharedPreferences pref;
    String email;

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
                                    pref = getSharedPreferences(prefs, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = pref.edit();
                                    Log.d("value", response.getJSONObject().get("id").toString());
                                    edit.putString("userId", response.getJSONObject().get("id").toString());
                                    edit.commit();
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
        if(isLoggedIn()){
            Intent i = new Intent(MainActivity.this, MainScreen.class);
            startActivity(i);
        }
        else {
            LogOut();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        cbManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onResume(){
        super.onResume();
        pref = getSharedPreferences(prefs, Context.MODE_PRIVATE);
        String uID = pref.getString("userId","");
        Toast.makeText(getApplicationContext(), "User ID: " + uID, Toast.LENGTH_LONG).show();
        if(isLoggedIn()){
            Intent i = new Intent(MainActivity.this, MainScreen.class);
            startActivity(i);
        }
        else{
            LogOut();
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public void LogOut(){
        pref = getSharedPreferences(prefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("userId", "");
        edit.commit();
    }
}
