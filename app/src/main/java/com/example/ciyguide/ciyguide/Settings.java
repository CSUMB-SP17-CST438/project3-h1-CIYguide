package com.example.ciyguide.ciyguide;

import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Marilyn Florek, 3/23/2017
 */
 
public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logOut = (Button) findViewById(R.id.logOut);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void LogOut(View view) {
        startActivity(new Intent(Settings.this, MainScreen.class));
    }

}
