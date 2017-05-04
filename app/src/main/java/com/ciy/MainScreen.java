package com.ciy;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ciy.R;

import java.util.ArrayList;


public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> searchphrases = new ArrayList<String>();
    private SurpriseMe surpriseMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        View searchrecipeButton = findViewById(R.id.search_recipes_button);
        searchrecipeButton.setOnClickListener(this);

        View previoussearchesButton = findViewById(R.id.previous_searches_button);
        previoussearchesButton.setOnClickListener(this);

        View surprisemeButton = findViewById(R.id.surprise_me_button);
        surprisemeButton.setOnClickListener(this);

        surpriseMe = new SurpriseMe();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // refresh the veggies image
        ImageView bg = (ImageView) findViewById(R.id.veggies);
        bg.requestLayout();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bg.getLayoutParams().height = 10;
        } else {
            bg.getLayoutParams().height = 250;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Account:
                    Intent i = new Intent(MainScreen.this, UserProfileActivity.class);
                    startActivity(i);
                    return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(MainScreen.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onClick(View v) {

        if (v.getId() == R.id.search_recipes_button) {
            Intent i = new Intent(MainScreen.this, SearchRecipeScreen.class);
            startActivity(i);
        }

        else if(v.getId() == R.id.previous_searches_button)
        {
            Intent i = new Intent(MainScreen.this, UserData.class);
            i.putExtra("activityFrom", "GetPrevious");
            startActivity(i);
        }

        else if(v.getId() == R.id.surprise_me_button)
        {
            searchphrases = surpriseMe.generateRandomList();
            Intent i = new Intent(MainScreen.this, RecipeList.class);
            i.putStringArrayListExtra("searchphrases", searchphrases);
            startActivity(i);
        }
    }

    @Override
    public void onResume(){super.onResume();}
}



