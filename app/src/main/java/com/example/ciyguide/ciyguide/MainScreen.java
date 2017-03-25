package com.example.ciyguide.ciyguide;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        View searchrecipeButton = findViewById(R.id.search_recipes_button);
        searchrecipeButton.setOnClickListener(this);

        View userprofileButton = findViewById(R.id.user_profile_button);
        userprofileButton.setOnClickListener(this);

        View previoussearchesButton = findViewById(R.id.previous_searches_button);
        previoussearchesButton.setOnClickListener(this);

        View surprisemeButton = findViewById(R.id.surprise_me_button);
        surprisemeButton.setOnClickListener(this);

    }

    public void onClick(View v) {

        if (v.getId() == R.id.search_recipes_button) {
            Intent i = new Intent(MainScreen.this, SearchRecipeScreen.class);
            startActivity(i);
        }

        else if(v.getId() == R.id.user_profile_button)
        {

        }

        else if(v.getId() == R.id.previous_searches_button)
        {

        }

        else if(v.getId() == R.id.surprise_me_button)
        {

        }
    }
}



