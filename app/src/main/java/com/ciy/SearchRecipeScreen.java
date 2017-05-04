package com.ciy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnKeyListener;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by jason on 3/24/2017.
 */

public class SearchRecipeScreen extends AppCompatActivity implements View.OnClickListener, OnKeyListener{

    EditText searchterm;
    ListView searchlist;
    ArrayList<String> searchphrases;
    ArrayAdapter<String> adapter;

    private static final int ACTIVITY_START_CAMERA_APP = 23;
    private static final int ACTIVITY_CLARIFAI_CLASS = 20;

    public String getClarifai_id(){return getString(R.string.clarifai_id);}
    public String getClarifai_secret(){return getString(R.string.clarifai_secret);}
    public ClarifaiClient client;
    public byte[] jpegImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = new ClarifaiBuilder(getString(R.string.clarifai_id), getString(R.string.clarifai_secret))
                // Optionally customize HTTP client via a custom OkHttp instance
                .client(new OkHttpClient.Builder()
                        .readTimeout(30, TimeUnit.SECONDS) // Increase timeout for poor mobile networks

                        // Log all incoming and outgoing data
                        // NOTE: You will not want to use the BODY log-level in production, as it will leak your API request details
                        // to the (publicly-viewable) Android log
                        .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override public void log(String logString) {
                                Timber.e(logString);
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                )
                .buildSync(); // use build() instead to get a Future<ClarifaiClient>, if you don't want to block this thread

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
        if(resultCode == RESULT_OK)
        {
            if(requestCode == ACTIVITY_START_CAMERA_APP)
            {
                Bundle extras = data.getExtras();
                Bitmap photoCaptureBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                photoCaptureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                jpegImage = os.toByteArray();
                if (jpegImage != null) {
                    new ClarifaiPrediction(this).execute();
                }
            }
            else if(requestCode == ACTIVITY_CLARIFAI_CLASS)
            {
                ArrayList<String> output = new ArrayList<String>();
                output = data.getStringArrayListExtra("predictionResults");
                for(int ii = 0; ii < output.size(); ii++)
                {
                    searchphrases.add(output.get(ii));
                    adapter.notifyDataSetChanged();
                }
            }


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

    public void setList(ArrayList<String> searchphrases)
    {
        for(int ii = 0; ii < searchphrases.size(); ii++)
        {
            this.searchphrases.add(searchphrases.get(ii));
            adapter.notifyDataSetChanged();
        }
    }
}
