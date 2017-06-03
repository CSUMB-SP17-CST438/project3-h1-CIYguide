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

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joe Otter on 5/24/2017.
 */

public class RecipeView extends RelativeLayout implements ListPrevSaved.AsyncResponse{
    private TextView tv;
    private ImageView iv;
    private Button b;

    public static RecipeView inflate(ViewGroup parent){
        RecipeView rv = (RecipeView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_view, parent, false);
        return rv;
    }

    public RecipeView(Context c){
        this(c, null);
    }

    public RecipeView(Context c, AttributeSet a){
        this(c, a, 0);
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
        ListPrevSaved.DownloadImage DL = new ListPrevSaved.DownloadImage(PS.getImage());
        DL.delegate = this;
        DL.execute();
        tv.setText(PS.getName());
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

    @Override
    public void processFinish(Bitmap output){
        iv.setImageBitmap(output);
    }

}
