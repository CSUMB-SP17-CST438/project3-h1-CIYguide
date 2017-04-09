package com.example.ciyguide.ciyguide;


import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

/**
 * Created by Marilyn Florek, 3/23/2017
 */

public class UserProfileActivity extends AppCompatActivity {

    private GoogleApiClient client;
    TextView myText;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button fridge = (Button) findViewById(R.id.myFridge);
        Button mine = (Button) findViewById(R.id.myRecipes);
        Button saved = (Button) findViewById(R.id.savedRecipes);
        Button settingsButton = (Button) findViewById(R.id.settings);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        MainActivity name = new MainActivity();
        myText = (TextView) findViewById(R.id.userTextView);
        myText.setText(name.name);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

//    public void editableUserName(View view) {
//        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.editableUserName);
//        switcher.showNext();
//        myText = (TextView) switcher.findViewById(R.id.userTextView);
//        EditText myNewText = (EditText) switcher.findViewById(R.id.userEditText);
//        myText.setText(myNewText.getText());
//    }

    public void myFridge(View view) {
        startActivity(new Intent(UserProfileActivity.this, IngredientsList.class));
    }

    public void myRecipes(View view) {
        startActivity(new Intent(UserProfileActivity.this, RecipeList.class));
    }

    public void savedRecipes(View view) {
        startActivity(new Intent(UserProfileActivity.this, RecipeList.class));
    }

    public void LogOut(View view) {
        MainActivity.LoggingOut();
        startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
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
