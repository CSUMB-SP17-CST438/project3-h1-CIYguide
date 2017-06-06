package com.ciy;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Joe Otter on 6/5/2017.
 */

public class FridgeListFragment extends ListFragment {

    DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = super.onCreateView(inflater, container, savedInstanceState);

        db = new DBHandler(v.getContext());

        ArrayList<FridgeItem> items = db.getFridgeItems();
        setListAdapter(new FridgeItemAdapter(getActivity(), items));

        return v;
    }
}
