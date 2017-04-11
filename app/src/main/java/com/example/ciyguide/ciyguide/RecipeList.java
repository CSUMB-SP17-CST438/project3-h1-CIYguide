package com.example.ciyguide.ciyguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by jsagi on 3/27/2017.
 */

public class RecipeList extends AppCompatActivity implements View.OnClickListener{

    ArrayList<String> searchphrases; //Added by MFlorek

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        View recipeSelector = findViewById(R.id.recipe_list_button);
        recipeSelector.setOnClickListener(this);
        Intent i = getIntent();
        searchphrases = i.getStringArrayListExtra("searchphrases"); //Added by MFlorek
//        HttpResponse<JsonNode> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=apples%2Cflour%2Csugar&limitLicense=false&number=5&ranking=1")
//                .header("X-Mashape-Key", "1XokUiq5Brmshk5I599juByZoaUtp1L2zstjsnbNwMQ9v1F3FE")
//                .header("Accept", "application/json").asJson();
    }

    public void onResume(){
        super.onResume();
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.recipe_list_button)
        {
            Intent i = new Intent(RecipeList.this, Placeholder.class);
            try {
                i.putStringArrayListExtra("searchphrases", searchphrases);
            } catch(Exception e){}
            startActivity(i);
        }
        else
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Account:
                Intent i = new Intent(RecipeList.this, UserProfileActivity.class);
                startActivity(i);
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(RecipeList.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
