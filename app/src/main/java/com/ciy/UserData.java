package com.ciy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserData extends AppCompatActivity {

    String username;
    String activityFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Bundle extras = getIntent().getExtras();
        if(extras == null){}
        else {
            activityFrom = extras.getString("activityFrom");
            if(activityFrom == "GetRecipes"){
                getStoredRecipes();
            }
            else if(activityFrom == "GetFridge"){
                getFridgeItems();
            }
            else if(activityFrom == "GetPrevious"){
            }
        }
//        searchphrases = i.getStringArrayListExtra("searchphrases");
//        username = i.getStringExtra("username");
    }

    @Override
    public void onResume() {
        super.onResume();
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
                startActivity(new Intent(UserData.this, MainScreen.class));
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(UserData.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void getStoredRecipes(){
        Toast.makeText(this, "working", Toast.LENGTH_LONG).show();

    }

    public void getFridgeItems(){
        Toast.makeText(this, "working", Toast.LENGTH_LONG).show();

    }
}
