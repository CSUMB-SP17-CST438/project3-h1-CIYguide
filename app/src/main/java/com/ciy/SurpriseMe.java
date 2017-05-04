package com.ciy;


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

        fruits.add("apple");
        fruits.add("lemon");
        fruits.add("banana");
        fruits.add("cherry");
        fruits.add("watermelon");
        fruits.add("jackfruit");
        fruits.add("kiwi");

        vegetables.add("cabbage");
        vegetables.add("tomato");
        vegetables.add("carrot");
        vegetables.add("lettuce");
        vegetables.add("onion");
        vegetables.add("eggplant");
        vegetables.add("leek");
        vegetables.add("broccoli");
        vegetables.add("brussels sprouts");

    }

    public ArrayList<String> generateRandomList ()
    {
        if(!searchphrases.isEmpty())
            searchphrases.clear();
        Random rand = new Random();
        dishid = rand.nextInt(3);
        int nbr = rand.nextInt(3) + 1;
        int picker;
        if(dishid == 0)
        {
            for(int ii = 0; ii < nbr; ii++)
            {
                picker = rand.nextInt(5);
                searchphrases.add(meat.get(picker));
            }
        }
        else if(dishid == 1)
        {
            for(int ii = 0; ii < nbr; ii++)
            {
                picker = rand.nextInt(9);
                searchphrases.add(vegetables.get(picker));
            }
        }
        else
        {
            for(int ii = 0; ii < nbr; ii++)
            {
                picker = rand.nextInt(7);
                searchphrases.add(fruits.get(picker));
            }
        }
        return searchphrases;
    }
}
