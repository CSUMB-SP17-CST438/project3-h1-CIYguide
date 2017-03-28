package com.example.ciyguide.ciyguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jason on 3/24/2017.
 */

public class SearchRecipeScreen extends AppCompatActivity implements View.OnClickListener {

    EditText searchterm;
    ListView searchlist;
    ArrayList<String> searchphrases;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe_screen);

        searchterm = (EditText)findViewById(R.id.search_text_box);
        searchlist = (ListView)findViewById(R.id.list_search_tags);

        searchphrases = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(SearchRecipeScreen.this, android.R.layout.simple_list_item_1,
                searchphrases);
        searchlist.setAdapter(adapter);
        View addingredientButton = findViewById(R.id.add_button);
        addingredientButton.setOnClickListener(this);

        View resultingrecipesButton = findViewById(R.id.resulting_recipes_button);
        resultingrecipesButton.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.add_button)
        {
           String result = searchterm.getText().toString();
            searchphrases.add(result);
            adapter.notifyDataSetChanged();
        }

        else if(v.getId() == R.id.resulting_recipes_button)
        {
            Intent i = new Intent(SearchRecipeScreen.this, RecipeList.class);
            startActivity(i);
        }
    }
}
