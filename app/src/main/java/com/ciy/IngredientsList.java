package com.ciy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ciy.R;


/**
 * Created by jsagi on 3/27/2017.
 */

public class IngredientsList extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Account:
                Intent i = new Intent(IngredientsList.this, MainScreen.class);
                startActivity(i);
                return true;

            case R.id.home:
                startActivity(new Intent(IngredientsList.this, MainScreen.class));
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(IngredientsList.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onResume(){
        super.onResume();
    }


}
