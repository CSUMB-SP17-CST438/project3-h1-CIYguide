package com.example.ciyguide.ciyguide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnKeyListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jason on 3/24/2017.
 */

public class SearchRecipeScreen extends AppCompatActivity implements View.OnClickListener, OnKeyListener{

    EditText searchterm;
    ListView searchlist;
    ArrayList<String> searchphrases;
    ArrayAdapter<String> adapter;

    private static final int ACTIVITY_START_CAMERA_APP = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        searchterm = (EditText)findViewById(R.id.search_text_box);
        searchterm.setSingleLine(true); //Added by MFlorek
        searchlist = (ListView)findViewById(R.id.list_search_tags);

        searchphrases = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(SearchRecipeScreen.this, R.layout.layout_search_list, R.id.search_term, searchphrases);
        searchlist.setAdapter(adapter);
        View addingredientButton = findViewById(R.id.add_button);
        addingredientButton.setOnClickListener(this);

        View resultingrecipesButton = findViewById(R.id.resulting_recipes_button);
        resultingrecipesButton.setOnClickListener(this);

        View cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(this);

        searchterm.setOnKeyListener(this);
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.add_button)
        {
           String result = searchterm.getText().toString();
            searchphrases.add(result);
            adapter.notifyDataSetChanged();
            searchterm.setText("");
            //Added by MFlorek - hides Keyboard when "Add" is clicked
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchterm.getWindowToken(), 0);
        }

        else if(v.getId() == R.id.resulting_recipes_button)
        {
            Intent i = new Intent(SearchRecipeScreen.this, RecipeList.class);
            i.putStringArrayListExtra("searchphrases", searchphrases);
            startActivity(i);
        }

        else if(v.getId() == R.id.camera_button)
        {
            Intent i = new Intent();
            i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, ACTIVITY_START_CAMERA_APP);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Intent i = new Intent(SearchRecipeScreen.this, ShowPhoto.class);
            i.putExtras(extras);
            startActivity(i);
        }
    }

    //Added by Marilyn Florek
    //This is so when someone presses the "Enter" Key on the Keyboard, it will
    //add the item to the list and close the keyboard
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode == EditorInfo.IME_ACTION_SEARCH || keyCode == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                switch (view.getId()) {
                    case R.id.search_text_box:
                        String result = searchterm.getText().toString();
                        if(result!=null){
                            searchphrases.add(result);
                            adapter.notifyDataSetChanged();
                            searchterm.setText("");
                        }
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(searchterm.getWindowToken(), 0);
                        break;
                }
            }
        }
        return false;
    }


    //The following code added by Marilyn Florek for the toolbar
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

            case R.id.home:
                startActivity(new Intent(SearchRecipeScreen.this, MainScreen.class));
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(SearchRecipeScreen.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void deleteSearchTerm(View view)
    {
        try {
            View parent = (View) view.getParent();
            TextView taskTextView = (TextView)
                    parent.findViewById(R.id.search_term);
            String term = String.valueOf(taskTextView.getText());
            searchphrases.remove(term);
            adapter.notifyDataSetChanged();
        }
        catch(Exception e){
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
