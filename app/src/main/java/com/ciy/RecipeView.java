package com.ciy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joe Otter on 5/24/2017.
 */

public class RecipeView extends RelativeLayout{
    private TextView tv;
    private ImageView iv;
    private Button b;

    public static RecipeView inflate(ViewGroup parent){
        RecipeView rv = (RecipeView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_view, parent, false);
        return rv;
    }

    public RecipeView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.item_recipe, this, true);
        setupChildren();
    }

    private void setupChildren(){
        tv = (TextView)findViewById(R.id.recipe_name);
        iv = (ImageView)findViewById(R.id.recipe_image);
        b = (Button)findViewById(R.id.select_recipe);
    }

    public void setPrevSav(final PreviousSaved PS){
        tv.setText(PS.getName());
        new DownloadImage(PS.getImage(), iv).execute();
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), SingleRecipe.class);
                i.putStringArrayListExtra("whatYouNeed", PS.getIngredients());
                i.putExtra("CookIt", PS.getUrl());
                i.putExtra("recipeName", PS.getName());
                i.putStringArrayListExtra("everything", PS.getIngredients());
                view.getContext().startActivity(i);
            }
        });
    }

    //class to get and set recipe image bmp to imageview : lorenzo
    public class DownloadImage extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView rIMG;

        public DownloadImage(String url, ImageView rIMG){
            this.url = url;
            this.rIMG = rIMG;
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
            iv.setImageBitmap(result);
        }
    }
}
