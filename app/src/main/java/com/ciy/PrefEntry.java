package com.ciy;


import java.util.ArrayList;
import java.util.Arrays;

public class PrefEntry {

    public static ArrayList<String> DIET_LABELS = new ArrayList<String>(Arrays.asList(
       "balanced", "high-protein", "high-fiber", "low-fat", "low-carb", "low-sodium"
    ));

    private String label;
    private String checked;

    public PrefEntry(){
        this.label = null;
        this.checked = null;
    }

    public PrefEntry(String l, String c){
        this.label = l;
        this.checked = c;
    }

    public String getLabel(){
        return this.label;
    }

    public String getChecked(){
        return this.checked;
    }

    public void setLabel(String l){
        this.label = l;
    }

    public void setChecked(String c){
        this.checked = c;
    }

    @Override
    public String toString(){
        return this.label + " " + this.checked;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof PrefEntry){
            PrefEntry p = (PrefEntry)o;
            if(p.toString().equals(o.toString()))
                return true;
        }
        return false;
    }
}