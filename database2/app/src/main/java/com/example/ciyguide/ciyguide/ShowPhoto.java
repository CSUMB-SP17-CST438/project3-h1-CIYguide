package com.example.ciyguide.ciyguide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by jason on 4/18/2017.
 */

public class ShowPhoto extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView mPhotoCapturedImageView;
        setContentView(R.layout.layout_photo);
        mPhotoCapturedImageView = (ImageView) findViewById(R.id.display_photo);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        Bitmap photoCaptureBitmap = (Bitmap) extras.get("data");
        mPhotoCapturedImageView.setImageBitmap(photoCaptureBitmap);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(.8*width), (int)(height*.8));

    }
}
