package com.ciy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Joe Otter on 5/24/2017.
 */

public class PreviousSaved {
    private String imageURL;
    private String name;
    private String url;
    private ArrayList<String> ingredients;

    public PreviousSaved(){
        this.imageURL = "";
        this.name = "";
        this.url = "";
        this.ingredients = new ArrayList<String>();
    }
    public PreviousSaved(String i, String n, String u, ArrayList<String> l)
    {
        this.imageURL = i;
        this.name = n;
        this.url = u;
        this.ingredients = l;
    }

    //getters
    public void setImage(String iurl){
        this.imageURL = iurl;
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
    public void setIngredients(String e){
        String[] ing = e.split(",");
        this.ingredients = new ArrayList(Arrays.asList(ing));
    }

    //setters
    public String getImage(){
        return this.imageURL;
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
    public String getStringIngredients(){
        String returnMe = "";
        for(int i = 0; i < ingredients.size(); i++){
            if(i == ingredients.size()-1)
                returnMe+= ingredients.get(i);
            else
                returnMe+= ingredients.get(i) + ",";
        }
        return returnMe;
    }

    @Override
    public String toString(){
        return this.name + " " + this.imageURL + " " + this.url + " " + this.ingredients.toString();
    }
}
