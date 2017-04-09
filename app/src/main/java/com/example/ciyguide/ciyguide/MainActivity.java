package com.example.ciyguide.ciyguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

    CallbackManager cbManager;
    String email;
    private AccessToken fbAccessToken;
    public static final String PREFS_NAME = "Preferences"; //Added by MF
    public static String name = "username"; //Added by MF
    public static String url = "url"; //Added by MF


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cbManager = CallbackManager.Factory.create();
        LoginButton login = (LoginButton) findViewById(R.id.login_button);
        login.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        LoginManager.getInstance().registerCallback(cbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback(){
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response){
                                Log.d("SHOW ME THE MONEY", response.toString());
                                try {
                                    fbAccessToken = loginResult.getAccessToken();

                                    //Following code Added by Marilyn F
                                    name = object.getString("name").toString();
                                    url = object.getString("picture").toString();
                                    Toast.makeText(MainActivity.this, "url = " + url, Toast.LENGTH_SHORT).show();
                                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("user_name", name);
                                    editor.commit();
                                }
                                catch (Exception e){
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        cbManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onResume(){
        super.onResume();

        //Following code Added by Marilyn Florek
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        name = prefs.getString("user_name","No name defined");

        if(isLoggedIn()){
            Intent i = new Intent(MainActivity.this, MainScreen.class);
            startActivity(i);
        }
    }

    public boolean isLoggedIn(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public static void LoggingOut(){
        LoginManager.getInstance().logOut();
    }
}
