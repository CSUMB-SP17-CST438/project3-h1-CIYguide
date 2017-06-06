package com.ciy;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ciy.FridgeItem;
import com.ciy.FridgeView;

import java.util.List;

/**
 * Created by Joe Otter on 6/5/2017.
 */

public class FridgeItemAdapter extends ArrayAdapter<FridgeItem> {

    public FridgeItemAdapter(Context c, List<FridgeItem> items){
        super(c, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        FridgeView fv = (FridgeView)convertView;
        if(null == fv)
            fv = FridgeView.inflate(parent);
        fv.setFridgeItems(getItem(position));
        return fv;
    }
}
