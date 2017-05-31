package com.ciy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe Otter on 5/24/2017.
 */

public class RecipeAdapter extends ArrayAdapter<PreviousSaved>{
    public RecipeAdapter(Context c, List<PreviousSaved> recipes){
        super(c, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RecipeView rv = (RecipeView)convertView;
        if(rv == null)
            rv = RecipeView.inflate(parent);
        rv.setPrevSav(getItem(position));
        return rv;
    }
}
