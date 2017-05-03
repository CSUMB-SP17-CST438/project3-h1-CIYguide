package com.ciy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserData extends AppCompatActivity {

    String username;
    String activityFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Bundle extras = getIntent().getExtras();
        if(extras == null){}
        else {
            activityFrom = extras.getString("activityFrom");

            try{
            if(activityFrom.equals("GetRecipes")){
                this.getStoredRecipes();
            }
            else if(activityFrom.equals("GetFridge")){
                this.getFridgeItems();
            }
            else if(activityFrom.equals("GetPrevious")){
                this.getPreviousSearches();
            }}
            catch (Exception e){
                Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
//        searchphrases = i.getStringArrayListExtra("searchphrases");
//        username = i.getStringExtra(";'l=username");
    }

    @Override
    public void onResume() {
        super.onResume();
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

            case R.id.home:
                startActivity(new Intent(UserData.this, MainScreen.class));
                return true;

            case R.id.Account:
                Intent i = new Intent(UserData.this, UserProfileActivity.class);
                startActivity(i);
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
        Toast.makeText(this, "Stored working", Toast.LENGTH_LONG).show();
    }

    public void getFridgeItems(){
        Toast.makeText(this, "Fridge working", Toast.LENGTH_LONG).show();
    }

    public void getPreviousSearches(){
        Toast.makeText(this, "Previous working", Toast.LENGTH_LONG).show();

        DBHandler db = new DBHandler(this);
        ArrayList<String> items = db.getPreviousSearches(username);

//        if(!items.isEmpty() || items==null) {
//            Toast.makeText(this, "if working", Toast.LENGTH_LONG).show();
//
////            ArrayAdapter<Hold> adapter = new ArrayAdapter<Hold>(this, android.R.layout.simple_list_item_1, items);
////            holds.setAdapter(adapter);
////
////            holds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                @Override
////                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                    cancelItem();
////                    cancelHold.this.id = id+1;
////                }
////            });
//        }
//        else
//        {
//            Toast.makeText(this, "else working", Toast.LENGTH_SHORT).show();
//
////            String[] list = new String[] { "No Holds Listed" };
////            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
////            holds.setAdapter(adapter2);
////            Toast.makeText(cancelHold.this, "No items to show", Toast.LENGTH_SHORT).show();
//        }
    }

}
