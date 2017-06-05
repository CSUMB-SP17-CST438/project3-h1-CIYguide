package com.ciy;

//source: https://www.tutorialspoint.com/android/android_json_parser.htm

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
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
import android.view.Display;
import android.view.GestureDetector;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.ciy.R;
import com.ciy.ScrollyScrolly;
/*
    created by Marilyn Florek, 3/22/2017

    edited and finished by Lhernandez
*/

public class SingleRecipe extends AppCompatActivity implements View.OnClickListener{

    String walmart_api_key = "t4gs8z827b4kqzm8n4e5nfgk";
    ScrollView sv;
    Uri.Builder builder = new Uri.Builder();
    String walmartAPIuri;
    String item;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 111 ;
    Button sendBtn;
    Button full;
    Button neededBTN;
    Button open_close;
    EditText txtMessage;
    EditText txtMessageNEED;
    String phoneNo;
    ScrollyScrolly recipePage;
    String message;
    TextView RecipeName;
    LinearLayout fullRecipe;
    LinearLayout neededOnly;
    ArrayList<String> searchphrases;
    ArrayList<String> whatYouHave;
    ArrayList<String> whatYouNeed;
    ArrayList<String> fullList;
    ArrayList<String> msg_parts;
    String messageTest = "Grocery List: \n";
    String have = "";
    String need = "";
    String r_Name = "";
    String r_URL = "";
    String imageURL = "";
    final int PICK_CONTACT=1;
    Cursor cursor1;
    int height;
    DBHandler db;

    //is used to determine if the user is coming from previous/saved
    String from_where = null;

    //issue with redirecting and losing the webpage! back button follows
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singe_recipe);

        //working on sending multiple sms messages
        msg_parts = new ArrayList<String>();

        //getting screen size to fit webview dynamically to any screen
        //height
        Display disp = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        int width = size.x;
        height = (size.y * 1)/2;

        //setting recipePage height
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width, height);
        params.height = height;

        //starting up webview
        recipePage = (ScrollyScrolly) findViewById(R.id.showMe);
        recipePage.setLayoutParams(params);
        WebSettings settings = recipePage.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        recipePage.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        //initializing ui variables

        //this button will change text message to full recipe
        full = (Button) findViewById(R.id.FULL);
        full.setOnClickListener(this);

        //this button will change text message to only neeeded items
        neededBTN = (Button) findViewById(R.id.NEEDED);
        neededBTN.setOnClickListener(this);

        //this button will toggle texting window
        open_close = (Button) findViewById(R.id.CLOSE);
        open_close.setOnClickListener(this);

        //needed to scroll programmatically
        sv = (ScrollView) findViewById(R.id.scrollEVERYTHING);

        //used to send text message
        sendBtn = (Button) findViewById(R.id.btnSendSMS);

        //used to go back in the webview
        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(this);
        back.setEnabled(false);
        back.setText("");

        //contains text message contents
        txtMessage = (EditText) findViewById(R.id.editText2);

        //display recipe name
        RecipeName = (TextView) findViewById(R.id.textView3);
        fullRecipe = (LinearLayout) findViewById(R.id.inside);


        //set height of buttons
        params = (ConstraintLayout.LayoutParams) full.getLayoutParams();
        params.height = height/6;
        full.setLayoutParams(params);
        params = (ConstraintLayout.LayoutParams) neededBTN.getLayoutParams();
        params.height = height/6;
        neededBTN.setLayoutParams(params);
        params = (ConstraintLayout.LayoutParams) open_close.getLayoutParams();
        params.height = height/4;
        open_close.setLayoutParams(params);
        params = (ConstraintLayout.LayoutParams) back.getLayoutParams();
        params.height = height/4;
        back.setLayoutParams(params);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        Intent i = getIntent();
        try {
            searchphrases = i.getStringArrayListExtra("searchphrases");

            //lorenzo
            whatYouHave = i.getStringArrayListExtra("whatYouHave");
            whatYouNeed = i.getStringArrayListExtra("whatYouNeed");
            fullList = i.getStringArrayListExtra("everything");
            r_Name = i.getStringExtra("recipeName");
            r_URL = i.getStringExtra("CookIt");
            imageURL = i.getStringExtra("imageURL");
            RecipeName.setText(r_Name.toString());
            from_where = i.getStringExtra("FROM");
        } catch(Exception e){}

        recipePage.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                enableGoBack(url);
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
                return false;
            }
        });

        //This is where it will take the data from the items the user input and will place it
        //into a string to send via SMS

        //creating grocery list based on have/need
        if(from_where == null) {
            messageTest += "What You Might Have:\n";
            for (int x = 0; x < whatYouHave.size(); x++)
                have += whatYouHave.get(x) + "\n";
            messageTest += have + "\nWhat You Need:\n";
            for (int x = 0; x < whatYouNeed.size(); x++)
                need += whatYouNeed.get(x) + "\n";
            messageTest += need;
            have = messageTest;
            txtMessage.setText(messageTest);

            //creating grocery list based on need only
            messageTest = "";
            need = "";
            messageTest = "What you Need:\n";
            for (int x = 0; x < whatYouNeed.size(); x++)
                need += whatYouNeed.get(x) + "\n";
            messageTest += need;
            neededBTN.setEnabled(true);
            neededBTN.setText("Needed Items");
            full.setEnabled(true);
            full.setText("All Items");
        }else if(from_where.equals("GetPrevious")){
            messageTest += "What you Need:\n";
            for(int x = 0; x < fullList.size(); x++){
                messageTest += fullList.get(x) + "\n";
            }
            txtMessage.setText(messageTest);
            full.setEnabled(false);
            full.setText("");
            neededBTN.setEnabled(false);
            neededBTN.setText("");
        }

        //init database connection
        db = new DBHandler(this);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try{
                    PreviousSaved ps = new PreviousSaved(imageURL, r_Name, r_URL, fullList);
                    db.addToTable(ps, 1);
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
        if(v.getId() == R.id.CLOSE){
            if(fullRecipe.getVisibility() == View.GONE) {
                //make texting area visible and scroll to bottom
                //only for full recipe
                open_close.setText("Close");
                fullRecipe.setVisibility(View.VISIBLE);
                sv.post(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(sv.getScrollY(), sv.getBottom() + 40);
                    }
                });
            }
            else {
                open_close.setText("Send Text");
                fullRecipe.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.FULL){
            txtMessage.setText(have);
        } else if (v.getId() == R.id.NEEDED && from_where == null){
            txtMessage.setText(need);
        }else if(v.getId() == R.id.go_back){
            recipePage.goBack();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri contactData = data.getData();
        Cursor c = getContentResolver().query(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            //retrieve phone number
            int phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String num = c.getString(phoneIndex);
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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                SmsManager smsManager = SmsManager.getDefault();
                if(message.length() > 160) {
                    msg_parts = smsManager.divideMessage(message);
                    smsManager.sendMultipartTextMessage(phoneNo, null, msg_parts, null, null);
                }else{
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                }
//                Toast.makeText(getApplicationContext(), "SMS sent.",
//                        Toast.LENGTH_LONG).show();

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                SmsManager smsManager = SmsManager.getDefault();
                if(message.length() > 160) {
                    msg_parts = smsManager.divideMessage(message);
                    smsManager.sendMultipartTextMessage(phoneNo, null, msg_parts, null, null);
                }else{
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                }
                //something here
            }
        }
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

    //webview has problems.  you can't go back if it redirects oh no!
    //came up with this temporary function solution until I can get swiping working
    private void enableGoBack(String checkMe){
        if(checkMe.equals(r_URL)) {
            back.setEnabled(false);
            back.setText("");
        }else{
            back.setEnabled(true);
            back.setText("Go Back");
        }
    }
}