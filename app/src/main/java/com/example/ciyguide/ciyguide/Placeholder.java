package com.example.ciyguide.ciyguide;

//source: https://www.tutorialspoint.com/android/android_json_parser.htm

import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import org.json.JSONObject;
import java.lang.String;
import java.util.ArrayList;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import static android.R.attr.id;
import static com.example.ciyguide.ciyguide.MainActivity.name;
/*
    created by Marilyn Florek, 3/22/2017
    This is just a placeholder screen for the Single Recipe
*/

public class Placeholder extends AppCompatActivity {

    String walmart_api_key = "t4gs8z827b4kqzm8n4e5nfgk";
    Uri.Builder builder = new Uri.Builder();
    String walmartAPIuri;
    String item;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 111 ;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;
    ArrayList<String> searchphrases;
    String messageTest = "Grocery List: ";
    final int PICK_CONTACT=1;
    Cursor cursor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtphoneNo = (EditText) findViewById(R.id.editText);
        txtphoneNo.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        txtMessage = (EditText) findViewById(R.id.editText2);

        Intent i = getIntent();
        try {
            searchphrases = i.getStringArrayListExtra("searchphrases");
        } catch(Exception e){}
        int num =1;

        //This is where it will take the data from the items the user input and will place it
        //into a string to send via SMS
        for(String s : searchphrases) {
            if(num++ == searchphrases.size())
            {
                messageTest += s + ". ";
            }
            else {
                messageTest += s + ", ";
            }
        }
        txtMessage.setText(messageTest);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                Toast.makeText(Placeholder.this, "Picking a contact", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, PICK_CONTACT);

                sendSMSMessage();
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        String number = "none";
        Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    if (c.moveToFirst()) {
//                        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
//
//                        Cursor cur = this.getContentResolver().
//                                query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                        null,
//                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
//                                        new String[]{}, null);
//                        while(cur.moveToNext())
//                        {
////                            number = cur.getString(
////                                    cur.getColumnIndex(
////                                            ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            int type = cur.getInt(
//                                    cur.getColumnIndex(
//                                            ContactsContract.CommonDataKinds.Phone.TYPE));
//                            Toast.makeText(this, number +" - "+ type, Toast.LENGTH_SHORT).show();
//
//                        }
                    }
                }
                break;
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
                Intent i = new Intent(Placeholder.this, UserProfileActivity.class);
                startActivity(i);
                return true;

            case R.id.LogOutSub:
                MainActivity.LoggingOut();
                startActivity(new Intent(Placeholder.this, MainActivity.class));
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

    protected void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        message = txtMessage.getText().toString();

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

        txtMessage.setText("");
        txtphoneNo.setText("");
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
