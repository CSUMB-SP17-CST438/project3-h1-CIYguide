package com.example.ciyguide.ciyguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by JSagisi/Mflorek on 3/27/2017.
 */

public class RecipeList extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> searchphrases; //Added by MFlorek

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        View recipeSelector = findViewById(R.id.recipe_list_button);
        recipeSelector.setOnClickListener(this);
        Intent i = getIntent();
        searchphrases = i.getStringArrayListExtra("searchphrases"); //Added by MFlorek
    }

    public void onResume() {
        super.onResume();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.recipe_list_button) {
            new AsyncCaller().execute("");
            Intent i = new Intent(RecipeList.this, Placeholder.class);
            try {
                i.putStringArrayListExtra("searchphrases", searchphrases);
            } catch(Exception e){}
            startActivity(i);
        } else {

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
                URL url = new URL("https://api.edamam.com/search?q=chicken&app_id=94f1de1c&app_key=841d3225b56e2736216e571b7197ebf9");

                connection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(connection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                result2 = result.toString();
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
//                JSONArray jArray = new JSONArray(result2);
//                for (int i = 0; i < jArray.length(); i++) {
//
////                    JSONObject jObject = jArray.getJSONObject(i);
//
//                }
                Toast.makeText(RecipeList.this, result2 , Toast.LENGTH_SHORT).show();
                Log.e("JSONFile", result2);
                this.progressDialog.dismiss();
            } catch (Exception e) {
                Log.e("JSONException", "Error: " + e.toString());
                Toast.makeText(RecipeList.this, "There is an error" , Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

    }
}

