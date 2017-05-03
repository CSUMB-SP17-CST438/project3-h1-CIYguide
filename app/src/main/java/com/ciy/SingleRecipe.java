package com.ciy;

//source: https://www.tutorialspoint.com/android/android_json_parser.htm

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import org.json.JSONObject;

import java.lang.String;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

<<<<<<< HEAD:app/src/main/java/com/ciy/SingleRecipe.java
<<<<<<< HEAD:app/src/main/java/com/example/ciyguide/ciyguide/SingleRecipe.java
import com.example.ciyguide.ciyguide.dummy.ScrollyScrolly;
=======
import com.ciy.R;
>>>>>>> e830d46e878a28a3f42ec463373e17d7d5c80357:app/src/main/java/com/ciy/SingleRecipe.java
=======
import com.ciy.ScrollyScrolly;
>>>>>>> 07e229fdf9f0cfa29465066f091f74361c000b53:app/src/main/java/com/ciy/SingleRecipe.java
/*
    created by Marilyn Florek, 3/22/2017
    This is just a placeholder screen for the Single Recipe
*/

public class SingleRecipe extends AppCompatActivity implements View.OnClickListener{

    String walmart_api_key = "t4gs8z827b4kqzm8n4e5nfgk";
    Uri.Builder builder = new Uri.Builder();
    String walmartAPIuri;
    String item;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 111 ;
    Button sendBtn;
    Button redirect_to_recipe_site;
    EditText txtMessage;
    EditText txtMessageNEED;
    String phoneNo;
    ScrollyScrolly recipePage;
    TextView full_recipe_send_section;
    TextView only_send_needed_section;
    String message;
    TextView RecipeName;
    LinearLayout fullRecipe;
    LinearLayout neededOnly;
    ArrayList<String> searchphrases;
    ArrayList<String> whatYouHave;
    ArrayList<String> whatYouNeed;
    String messageTest = "Grocery List: \n";
    String have = "";
    String need = "";
    String r_Name = "";
    String r_URL = "";
    final int PICK_CONTACT=1;
    Cursor cursor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singe_recipe);

        //starting up webview
        recipePage = (ScrollyScrolly) findViewById(R.id.showMe);
        WebSettings settings = recipePage.getSettings();
        settings.setJavaScriptEnabled(true);
        recipePage.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        redirect_to_recipe_site = (Button) findViewById(R.id.recipeURL);
        redirect_to_recipe_site.setOnClickListener(this);
        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtMessage = (EditText) findViewById(R.id.editText2);
        txtMessageNEED = (EditText) findViewById(R.id.editText2NEED);
        RecipeName = (TextView) findViewById(R.id.textView3);
        full_recipe_send_section = (TextView) findViewById(R.id.first_section);
        full_recipe_send_section.setOnClickListener(this);
        only_send_needed_section = (TextView) findViewById(R.id.second_section);
        only_send_needed_section.setOnClickListener(this);
        fullRecipe = (LinearLayout) findViewById(R.id.inside);
        neededOnly = (LinearLayout) findViewById(R.id.insideNEED);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        Intent i = getIntent();
        try {
            searchphrases = i.getStringArrayListExtra("searchphrases");

            //lorenzo
            whatYouHave = i.getStringArrayListExtra("whatYouHave");
            whatYouNeed = i.getStringArrayListExtra("whatYouNeed");
            r_Name = i.getStringExtra("recipeName");
            r_URL = i.getStringExtra("CookIt");
            RecipeName.setText(r_Name.toString());
        } catch(Exception e){}


        recipePage.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url){
            }

            //deprecated so unsure if needed
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            }
        });

        recipePage.loadUrl(r_URL);
        recipePage.setOnClickListener(this);
        recipePage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
//                Log.d("action", Integer.toString())
                return false;
            }
        });

        //This is where it will take the data from the items the user input and will place it
        //into a string to send via SMS

        //creating grocery list based on have/need
        messageTest += "What You Might Have:\n";
        for(int x = 0; x < whatYouHave.size(); x++)
            have += whatYouHave.get(x) + "\n";
        messageTest += have + "\nWhat You Need:\n";
        for(int x = 0; x < whatYouNeed.size(); x++)
            need += whatYouNeed.get(x) + "\n";
        messageTest += need;
        txtMessage.setText(messageTest);

        //creating grocery list based on need only
        messageTest = "";
        need = "";
        messageTest = "What you Need:\n";
        for(int x = 0; x < whatYouNeed.size(); x++)
            need += whatYouNeed.get(x) + "\n";
        messageTest += need;
        txtMessageNEED.setText(messageTest);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try{
                    Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(pickContact, 1);
                }
                catch(Exception e){
                    Log.e("ContactError", "Error: " + e.toString());
                }
            }
        });
    }

    //expand and contract sections of recipe list
    @Override
    public void onClick(View v){
        //creating accordion effect for texting options
        if(v.getId() == R.id.first_section){
            if(fullRecipe.getVisibility() == View.GONE)
                fullRecipe.setVisibility(View.VISIBLE);
            else
                fullRecipe.setVisibility(View.GONE);
        } else if (v.getId() == R.id.second_section) {
            if(neededOnly.getVisibility() == View.GONE)
                neededOnly.setVisibility(View.VISIBLE);
            else
                neededOnly.setVisibility(View.GONE);
        }else if (v.getId() == R.id.recipeURL)
        {
            //creating accordion effect for webview
            int h = recipePage.getHeight();
            float d = getResources().getDisplayMetrics().density;
            float dh = h/d;
            if(dh < 3) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) recipePage.getLayoutParams();
                params.height = getResources().getDimensionPixelSize(R.dimen.webView_height_clicked);
                recipePage.setLayoutParams(params);
            }else{
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) recipePage.getLayoutParams();
                params.height = getResources().getDimensionPixelSize(R.dimen.webView_height_unclicked);
                recipePage.setLayoutParams(params);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri contactData = data.getData();
        Cursor c = getContentResolver().query(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            int phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String num = c.getString(phoneIndex);
//            Toast.makeText(Placeholder.this, "Number=" + num, Toast.LENGTH_LONG).show();
            sendSMSMessage(num);
        }
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
                Intent i = new Intent(SingleRecipe.this, UserProfileActivity.class);
                startActivity(i);
                return true;

            case R.id.home:
                startActivity(new Intent(SingleRecipe.this, MainScreen.class));
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(SingleRecipe.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    protected void getItem(View v) {

        HttpHandler sh = new HttpHandler();

        builder.scheme("http")
                .authority("api.walmartlabs.com")
                .appendPath("v1")
                .appendPath("search")
                .appendQueryParameter("query", item)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("apiKey", walmart_api_key);
        walmartAPIuri = builder.build().toString();
        String jsonStr = sh.makeServiceCall(walmartAPIuri);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "There was an error",Toast.LENGTH_SHORT).show();
            }
        }

    }

    protected void sendSMSMessage(String number) {
        phoneNo = number;
        message = txtMessage.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
//        txtMessage.setText("");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}