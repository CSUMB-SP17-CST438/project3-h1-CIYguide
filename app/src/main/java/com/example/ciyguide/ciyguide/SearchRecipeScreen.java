package com.example.ciyguide.ciyguide;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jason on 3/24/2017.
 */

public class SearchRecipeScreen extends AppCompatActivity implements View.OnClickListener {

    EditText searchterm;
    ListView searchlist;
    ArrayList<String> searchphrases;
    ArrayAdapter<String> adapter;

    private static final int ACTIVITY_START_CAMERA_APP = 23;

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

        View cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(this);

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

        else if(v.getId() == R.id.camera_button)
        {
            Intent i = new Intent();
            i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i,ACTIVITY_START_CAMERA_APP);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK)
        {
            Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(SearchRecipeScreen.this, UserProfileActivity.class);
                startActivity(i);
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(SearchRecipeScreen.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
