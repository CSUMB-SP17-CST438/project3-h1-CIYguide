package com.ciy;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Joe Otter on 6/5/2017.
 */

public class FridgeView extends RelativeLayout {
    private TextView amtdisp;
    private TextView namedisp;
    private Button remove;
    private Button search;
    private ImageView image;

    //inflate the view
    public static FridgeView inflate(ViewGroup parent){
        FridgeView fv = (FridgeView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fridge_view, parent, false);
        return fv;
    }

    public FridgeView(Context c){this(c, null);}
    public FridgeView(Context c, AttributeSet a){this(c, a, 0);}
    public FridgeView(Context c, AttributeSet a, int defStyle){
        super(c, a, defStyle);
        LayoutInflater.from(c).inflate(R.layout.fridge_item, this, true);
        setupChildren();
    }

    //initializes elements in the activity
    private void setupChildren(){
        amtdisp = (TextView)findViewById(R.id.item_amt);
        namedisp = (TextView)findViewById(R.id.item_name);
        remove = (Button)findViewById(R.id.remove_from_fridge);
        search = (Button)findViewById(R.id.find_recipe);
        image = (ImageView)findViewById(R.id.fridge_item_picture);
    }

    //set up elements in the activity
    public void setFridgeItems(final FridgeItem f){
        amtdisp.setText(Integer.toString(f.getAmount()) + "x");
        namedisp.setText(f.getIngredient());
        image.setImageResource(R.drawable.ciyguidelogo);
        remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler db = new DBHandler(view.getContext());
                db.removeFridgeItem(namedisp.getText().toString());
                FridgeListFragment.f.clear();
                FridgeListFragment.f.addAll(db.getFridgeItems());
                FridgeListFragment.f.notifyDataSetChanged();
            }
        });
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(search.getContext(), SearchRecipeScreen.class);
                i.putExtra("SEARCH", namedisp.getText().toString());
                search.getContext().startActivity(i);
            }
        });
    }
}
