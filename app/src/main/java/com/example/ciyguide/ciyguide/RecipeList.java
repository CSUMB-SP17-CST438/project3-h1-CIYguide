package com.example.ciyguide.ciyguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.data;
import static org.apache.http.util.CharsetUtils.get;

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
        HttpURLConnection response;
        getJSON();
//        String data = getJSON("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=apples%2Cflour%2Csugar&limitLicense=false&number=5&ranking=1");
//        AuthMsg msg = new Gson().fromJson(data, AuthMsg.class);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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

    public void getJSON()
    {
        String url = "https://api.edamam.com/search?q=chicken&app_id=$94f1de1c&app_key=$841d3225b56e2736216e571b7197ebf9";
        HttpURLConnection c = null;
        int timeout = 1000;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();
        } catch(Exception e){
            Toast.makeText(this, "There was an error!!!", Toast.LENGTH_SHORT).show();
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
