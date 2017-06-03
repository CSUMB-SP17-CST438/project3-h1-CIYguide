package com.ciy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListPrevSaved extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//    public static Bitmap getBitmap(String urlSTRING){
//        Bitmap b = null;
//
//        try {
//            URL url = new URL(urlSTRING);
//            b = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        Log.d("RV", urlSTRING);
//
//        return b;
//    }

    //class to get and set recipe image bmp to imageview : lorenzo
    public static class DownloadImage extends AsyncTask<Void, Void, Bitmap>{

        private String url;

        public DownloadImage(String url){
            this.url = url;
        }

        public AsyncResponse delegate = null;

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
        public void processFinish(Bitmap output);
    }
}


