package com.ciy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserData extends AppCompatActivity {

    String username;
    String activityFrom;
    TextView header;
    ListView searchlist;
    ArrayAdapter<String> adapter;
    ArrayList<String> searchphrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        header = (TextView) findViewById(R.id.myheader);
        searchlist = (ListView) findViewById(R.id.list_info);
        searchphrases = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(UserData.this, R.layout.layout_search_list, R.id.search_term, searchphrases);
        searchlist.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if(extras == null){}
        else {
            activityFrom = extras.getString("activityFrom");
            if(extras.getString("username")!=null)
            {
                username = extras.getString("username");
            }
            try{
            if(activityFrom.equals("GetRecipes")){
                header.setText("Stored Recipes");
                getStoredRecipes();
            }
            else if(activityFrom.equals("GetFridge")){
                header.setText("Fridge Items");
                getFridgeItems();
            }
            else if(activityFrom.equals("GetPrevious")){
                header.setText("Previous Searches");
                getPreviousSearches();
            }}catch(Exception e){}
        }
//        searchphrases = i.getStringArrayListExtra("searchphrases");
//        username = i.getStringExtra("username");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                startActivity(new Intent(UserData.this, MainScreen.class));
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(UserData.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void getStoredRecipes(){
        Toast.makeText(this, "working", Toast.LENGTH_LONG).show();

    }

    public void getFridgeItems(){
        Toast.makeText(this, "working", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Username: " + username, Toast.LENGTH_SHORT).show();

    }

    public void getPreviousSearches(){
        DBHandler db = new DBHandler(this);

        Toast.makeText(this, "Username: " + username, Toast.LENGTH_SHORT).show();
//        searchphrases = db.getPreviousSearches(username);


    }

}
