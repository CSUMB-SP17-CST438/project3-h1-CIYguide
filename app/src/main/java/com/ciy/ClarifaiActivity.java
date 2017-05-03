package com.ciy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.ciy.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by jason on 4/18/2017.
 */

public class ClarifaiActivity extends Activity implements View.OnClickListener{

    @Nullable
    private ClarifaiClient client;
    private byte[] jpegImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        client = new ClarifaiBuilder(getString(R.string.clarifai_id), getString(R.string.clarifai_secret))
                // Optionally customize HTTP client via a custom OkHttp instance
                .client(new OkHttpClient.Builder()
                        .readTimeout(30, TimeUnit.SECONDS) // Increase timeout for poor mobile networks

                        // Log all incoming and outgoing data
                        // NOTE: You will not want to use the BODY log-level in production, as it will leak your API request details
                        // to the (publicly-viewable) Android log
                        .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override public void log(String logString) {
                                Timber.e(logString);
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                )
                .buildSync(); // use build() instead to get a Future<ClarifaiClient>, if you don't want to block this thread


        super.onCreate(savedInstanceState);

        ImageView mPhotoCapturedImageView;
        setContentView(R.layout.activity_clarifai);
        mPhotoCapturedImageView = (ImageView) findViewById(R.id.display_photo);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        Bitmap photoCaptureBitmap = (Bitmap) extras.get("data");
        mPhotoCapturedImageView.setImageBitmap(photoCaptureBitmap);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        photoCaptureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        jpegImage = os.toByteArray();

        View clarifaiButton = findViewById(R.id.choose_photo);
        clarifaiButton.setOnClickListener(this);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(.8*width), (int)(height*.8));

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.choose_photo)
        {
            ArrayList<String> predictions = new ArrayList<String>();

            //Enter code for Clarifai predict here//



            //************************************//


            //temporary output of class, delete when predictions code is done//
            predictions.add("tomato");
            predictions.add("pasta");
            predictions.add("meatball");
            ///////////////////////////////////////////////////////////////////


            Intent intent = new Intent();
            intent.putStringArrayListExtra("predictionResults", predictions);
            setResult(RESULT_OK, intent);
            finish();

        }
    }

    @NonNull
    public ClarifaiClient clarifaiClient() {
        final ClarifaiClient client = this.client;
        if (client == null) {
            throw new IllegalStateException("Cannot use Clarifai client before initialized");
        }
        return client;
    }

}
