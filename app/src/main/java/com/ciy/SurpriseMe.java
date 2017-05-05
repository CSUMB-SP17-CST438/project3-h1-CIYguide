package com.ciy;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by jason on 5/4/2017.
 */

public class SurpriseMe {
    ArrayList<String> searchphrases = new ArrayList<String>();
    ArrayList<String> meat = new ArrayList<String>();
    ArrayList<String> fruits = new ArrayList<String>();
    ArrayList<String> vegetables = new ArrayList<String>();

    int dishid;


    public SurpriseMe()
    {
        meat.add("beef");
        meat.add("pork");
        meat.add("chicken");
        meat.add("fish");
        meat.add("tofu");
        meat.add("tuna");
        meat.add("salmon");
        meat.add("duck");
        meat.add("egg");
        meat.add("lobster");
        meat.add("shrimp");
        meat.add("prawn");

        fruits.add("apple");
        fruits.add("lemon");
        fruits.add("banana");
        fruits.add("cherry");
        fruits.add("watermelon");
        fruits.add("kiwi");
        fruits.add("jackfruit");
        fruits.add("durian");
        fruits.add("orange");
        fruits.add("grapes");

        vegetables.add("cabbage");
        vegetables.add("tomato");
        vegetables.add("carrot");
        vegetables.add("lettuce");
        vegetables.add("onion");
        vegetables.add("eggplant");
        vegetables.add("leek");
        vegetables.add("broccoli");
        vegetables.add("brussels sprouts");
        vegetables.add("beat");
        vegetables.add("green pepper");
        vegetables.add("garlic");

    }

    public ArrayList<String> generateRandomList ()
    {
        if(!searchphrases.isEmpty())
            searchphrases.clear();

        Random rand = new Random();

        dishid = rand.nextInt(3);

        int picker;
        if(dishid == 0)
        {
            picker = rand.nextInt(meat.size());
            searchphrases.add(meat.get(picker));
        }
        else if(dishid == 1)
        {
            picker = rand.nextInt(vegetables.size());
            searchphrases.add(vegetables.get(picker));
        }
        else if(dishid == 2)
        {
            picker = rand.nextInt(fruits.size());
            searchphrases.add(fruits.get(picker));
        }
        return searchphrases;
    }
}
