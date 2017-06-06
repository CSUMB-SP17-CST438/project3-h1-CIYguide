package com.ciy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListPrevSaved extends FragmentActivity {

    public static final String WHERE_TO = "LPS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        try{
            SharedPreferences sp = getSharedPreferences(WHERE_TO, Context.MODE_PRIVATE);
            SharedPreferences.Editor spedit = sp.edit();
            spedit.putString("from_where", i.getStringExtra("activityFrom"));
            spedit.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_list_prev_saved);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    //class to get and set recipe image bmp to imageview : lorenzo
    public static class DownloadImage extends AsyncTask<Void, Void, Bitmap>{

        private String url;

        public AsyncResponse delegate = null;

        public DownloadImage(String url){
            this.url = url;
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
            delegate.processFinish(result);
        }
    }

    public interface AsyncResponse{
        void processFinish(Bitmap output);
    }
}


