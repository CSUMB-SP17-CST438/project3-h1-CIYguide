package com.ciy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ShowPreviousOrSaved extends ListFragment {

    DBHandler db;
    String loadWhat = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = super.onCreateView(inflater, container, savedInstanceState);

        db = new DBHandler(v.getContext());
        SharedPreferences sp = v.getContext().getSharedPreferences(ListPrevSaved.WHERE_TO, Context.MODE_PRIVATE);
        loadWhat = sp.getString("from_where", "");
        Log.d("SPOS", loadWhat);
        ArrayList<PreviousSaved> list = new ArrayList<PreviousSaved>();
        if(loadWhat.equals("GetPrevious"))
            list = db.getPrevOrSaveEntries(1);
        if(loadWhat.equals("GetRecipes"))
            list = db.getPrevOrSaveEntries(2);

        setListAdapter(new RecipeAdapter(getActivity(), list));

        return v;
    }
}
