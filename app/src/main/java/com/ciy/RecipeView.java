package com.ciy;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    }
}
