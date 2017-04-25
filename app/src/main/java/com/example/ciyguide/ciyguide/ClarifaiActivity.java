package com.example.ciyguide.ciyguide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by jason on 4/18/2017.
 */

public class ClarifaiActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView mPhotoCapturedImageView;
        setContentView(R.layout.activity_clarifai);
        mPhotoCapturedImageView = (ImageView) findViewById(R.id.display_photo);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        Bitmap photoCaptureBitmap = (Bitmap) extras.get("data");
        mPhotoCapturedImageView.setImageBitmap(photoCaptureBitmap);

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
            
        }
    }
}
