package com.example.ciyguide.ciyguide;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainScreen extends AppCompatActivity implements View.OnClickListener {

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
            Intent i = new Intent(MainScreen.this, RecipeList.class);
            startActivity(i);
        }

        else if(v.getId() == R.id.surprise_me_button)
        {

        }
    }

    @Override
    public void onResume(){super.onResume();}
}



