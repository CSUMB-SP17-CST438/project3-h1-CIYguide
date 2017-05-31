package com.ciy.dummy;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ciy.PreviousSaved;
import com.ciy.RecipeView;

import java.util.List;

/**
 * Created by Joe Otter on 5/29/2017.
 */

public class PrevSavAdapter extends ArrayAdapter<PreviousSaved>{

    public PrevSavAdapter(Context c, List<PreviousSaved> prevSaved){
        super(c, 0, prevSaved);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RecipeView rv = (RecipeView)convertView;
        if(null== rv)
            rv = RecipeView.inflate(parent);
        rv.setPrevSav(getItem(position));
        return rv;
    }
}
