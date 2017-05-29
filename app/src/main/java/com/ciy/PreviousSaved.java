package com.ciy;

import java.util.ArrayList;

/**
 * Created by Joe Otter on 5/24/2017.
 */

public class PreviousSaved {
    private String image;
    private String name;
    private String url;
    private ArrayList<String> ingredients;

    public PreviousSaved(){
        this.image = "";
        this.name = "";
        this.url = "";
        this.ingredients = new ArrayList<String>();
    }
    public PreviousSaved(String i, String n, String u, ArrayList<String> l)
    {
        this.image = i;
        this.name = n;
        this.url = u;
        this.ingredients = l;
    }

    //getters
    public void setImage(String iurl){
        this.image = iurl;
    }
    public void setName(String rname){
        this.name = rname;
    }
    public void setUrl(String webURL){
        this.url = webURL;
    }
    public void setIngredients(ArrayList<String> list){
        this.ingredients.addAll(list);
    }

    //setters
    public String getImage(){
        return this.image;
    }
    public String getName(){
        return this.name;
    }
    public String getUrl(){
        return this.url;
    }
    public ArrayList<String> getIngredients(){
        return this.ingredients;
    }
}
