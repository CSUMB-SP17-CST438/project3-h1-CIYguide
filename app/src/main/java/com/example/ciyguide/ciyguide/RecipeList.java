package com.example.ciyguide.ciyguide;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.HttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static org.apache.http.util.CharsetUtils.get;

/**
 * Created by JSagisi/Mflorek on 3/27/2017.
 * Edited by Lhernandez
 */

public class RecipeList extends FragmentActivity implements View.OnClickListener {

    ArrayList<String> searchphrases; //Added by MFlorek

    //lorenzo
    public static final String RECIPE_PREF = "Recipe info";
    public static String imgURL = "image";
    public static String recipeName = "name";
    public static String ENTIRE_RECIPE_JSON = "rJSON";
    public static String start = "start Index";
    public static String end = "end index";

    Button NextButton;
    Button BackButton;
    ImageView RecipeImage;
    TextView RecipeName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //lorenzo

        setContentView(R.layout.activity_recipe_list);

        //lorenzo

        View recipeSelector = findViewById(R.id.recipe_list_button);
        recipeSelector.setOnClickListener(this);
        NextButton = (Button) findViewById(R.id.Next_button);
        NextButton.setOnClickListener(this);
        BackButton = (Button) findViewById(R.id.Back_button);
        BackButton.setOnClickListener(this);
        RecipeImage = (ImageView) findViewById(R.id.RecipeImage);
        RecipeName = (TextView) findViewById(R.id.RecipeName);
        Intent i = getIntent();
        searchphrases = i.getStringArrayListExtra("searchphrases"); //Added by MFlorek

        //lorenzo
        SharedPreferences SP = getSharedPreferences(RECIPE_PREF, Context.MODE_PRIVATE);
        if(!SP.contains(start))
        {
            SharedPreferences.Editor SPedit = SP.edit();
            SPedit.putInt(start, 0);
            SPedit.putInt(end, 1);
            SPedit.commit();
        }

        //disable back button if at 0
        if(SP.getInt(start, 0) == 0)
        {
            BackButton.setClickable(false);
            BackButton.setTextColor(Color.parseColor("#D6D7D7"));
        }
    }

    public void onResume() {
        super.onResume();
    }


    public void onClick(View v) {
        if (v.getId() == R.id.recipe_list_button) {
//            Intent i = new Intent(RecipeList.this, Placeholder.class);
//            try {
//                i.putStringArrayListExtra("searchphrases", searchphrases);
//            } catch(Exception e){}
//            startActivity(i);
        } else if(v.getId() == R.id.Next_button){
            //incrementing for proper call to edamam : lorenzo
            SharedPreferences SP = getSharedPreferences(RECIPE_PREF, Context.MODE_PRIVATE);
            int x = SP.getInt(start, 0);
            int y = SP.getInt(end, 1);
            SharedPreferences.Editor SPedit = SP.edit();
            SPedit.putInt(start, x+1);
            SPedit.putInt(end, y+1);
            SPedit.commit();

            //check and enable click for back button : lorenzo
            if(SP.contains(start) && SP.getInt(start, 0) >= 1){
                BackButton.setClickable(true);
                BackButton.setTextColor(Color.parseColor("#000000"));
            }
            new AsyncCaller().execute("");
        } else if(v.getId() == R.id.Back_button){
            //decrementing for proper call to edamam : lorenzo
            SharedPreferences SP = getSharedPreferences(RECIPE_PREF, Context.MODE_PRIVATE);
            int x = SP.getInt(start, 0);
            //no need to decrement if x is less then 1 : lorenzo
            if(x >= 1) {
                int y = SP.getInt(end, 1);
                SharedPreferences.Editor SPedit = SP.edit();
                SPedit.putInt(start, x - 1);
                SPedit.putInt(end, y - 1);
                SPedit.commit();
                new AsyncCaller().execute("");
            }
            //check and disable backbutton : lorenzo
            if(SP.contains(start) && SP.getInt(start, 0) < 1){
                BackButton.setClickable(false);
                BackButton.setTextColor(Color.parseColor("#D6D7D7"));
            }
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

    public class AsyncCaller extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(RecipeList.this);
        InputStream inputStream = null;
        HttpURLConnection connection;
        StringBuilder result = new StringBuilder();
        String result2 = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Gathering your recipes...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            //source: https://developer.edamam.com/edamam-docs-recipe-api
            try {
                SharedPreferences Sp = getSharedPreferences(RECIPE_PREF, Context.MODE_PRIVATE);
                URL url = new URL("https://api.edamam.com/search?q=chicken,salt&app_id=94f1de1c&app_key=841d3225b56e2736216e571b7197ebf9&from=" + Sp.getInt(start, 0) + "&to=" + Sp.getInt(end, 1));

                connection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(connection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;

                //lorenzo
                //problem with getting links.  removing \ character
                while ((line = reader.readLine()) != null) {
                    line = line.replace("\\", "");
                    result.append(line);
                }
                result2 = result.toString();
                SharedPreferences SP = getSharedPreferences(RECIPE_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor SPedit = SP.edit();
                SPedit.putString(ENTIRE_RECIPE_JSON, result2);

                //store recipe to pass : lorenzo ^
                SPedit.commit();
            } catch (Exception e) {
            } finally {
                connection.disconnect();
            }

            try {

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void none) {
            super.onPostExecute(none);
            try {
                //grabbing image url for now : lorenzo
                JSONObject jObj = new JSONObject(result2);
                String hits = jObj.get("hits").toString();
                JSONArray jARR = new JSONArray(hits);
                JSONObject jObj2 = new JSONObject(jARR.getJSONObject(0).toString());
                JSONObject jObj3 = new JSONObject(jObj2.get("recipe").toString());

                SharedPreferences SP = getSharedPreferences(RECIPE_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor SPedit = SP.edit();
                SPedit.putString(imgURL, jObj3.get("image").toString());
                SPedit.putString(recipeName, jObj3.get("label").toString());
                SPedit.commit();

                new DownloadImage(jObj3.get("image").toString(), RecipeImage).execute();
                RecipeName.setText(jObj3.get("label").toString());

                Toast.makeText(RecipeList.this, jObj3.get("image").toString(), Toast.LENGTH_SHORT).show();
                this.progressDialog.dismiss();
            } catch (Exception e) {
                Log.e("JSONException", "Error: " + e.toString());
                Toast.makeText(RecipeList.this, "There is an error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }

    //class to get and set recipe image bmp to imageview : lorenzo
    public class DownloadImage extends AsyncTask<Void, Void, Bitmap>{

        private String url;
        private ImageView rIMG;

        public DownloadImage(String url, ImageView rIMG){
            this.url = url;
            this.rIMG = rIMG;
        }

        //get bitmap from url
        @Override
        protected Bitmap doInBackground(Void... params){
            try{
                URL urlConnect = new URL(this.url);
                HttpURLConnection connectMe = (HttpURLConnection) urlConnect.openConnection();
                connectMe.setDoInput(true);
                connectMe.connect();
                InputStream in = connectMe.getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(in);
                return bmp;
            }catch(Exception e){
                Log.e("Error", "" + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        //set bitmap from url
        @Override
        protected void onPostExecute(Bitmap result){
            super.onPostExecute(result);
            RecipeImage.setImageBitmap(result);
        }
    }
}