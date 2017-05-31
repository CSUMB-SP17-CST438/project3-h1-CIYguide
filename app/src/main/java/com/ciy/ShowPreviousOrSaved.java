package com.ciy;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = super.onCreateView(inflater, container, savedInstanceState);

        db = new DBHandler(v.getContext());
        ArrayList<PreviousSaved> fromDB = db.getPrevEntries();
        setListAdapter(new RecipeAdapter(getActivity(), fromDB));

        return v;
    }
}
