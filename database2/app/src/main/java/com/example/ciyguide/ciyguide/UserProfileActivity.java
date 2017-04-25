package com.example.ciyguide.ciyguide;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by Marilyn Florek, 3/23/2017
 */

public class UserProfileActivity extends AppCompatActivity {

    private GoogleApiClient client;
    TextView userName;
    ProfilePictureView proPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Button fridge = (Button) findViewById(R.id.myFridge);
        Button mine = (Button) findViewById(R.id.myRecipes);
        Button saved = (Button) findViewById(R.id.savedRecipes);
        Button settingsButton = (Button) findViewById(R.id.settings);
        userName = (TextView) findViewById(R.id.userTextView);
        proPic = (ProfilePictureView)findViewById(R.id.profilePicture);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Boolean gotProfile = getUserInfo();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public boolean getUserInfo(){
        Profile pro = Profile.getCurrentProfile();
        try {
            proPic.setProfileId(pro.getId());
            userName.setText(pro.getFirstName() + " " + pro.getLastName());
        }catch (Exception e) {
            Toast.makeText(this, "User is not logged in!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                startActivity(new Intent(UserProfileActivity.this, MainScreen.class));
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void myFridge(View view) {
        startActivity(new Intent(UserProfileActivity.this, IngredientsList.class));
    }

    public void myRecipes(View view) {
        startActivity(new Intent(UserProfileActivity.this, RecipeList.class));
    }

    public void savedRecipes(View view) {
        startActivity(new Intent(UserProfileActivity.this, RecipeList.class));
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserProfile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ciyguide.ciyguide/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserProfile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ciyguide.ciyguide/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
