package com.ciy;

/**
 * Created by Joe Otter on 6/4/2017.
 */

public class FridgeItem {
    private String ingredient;
    private int amount;

    public FridgeItem(){
        this.ingredient = "";
        this.amount = 0;
    }

    public FridgeItem(String i, int a){
        this.ingredient = i;
        this.amount = a;
    }

    //getters
    public String getIngredient(){
        return this.ingredient;
    }
    public int getAmount(){
        return this.amount;
    }

    //setters
    public void setIngredient(String i){
        this.ingredient = i;
    }
    public void setAmount(int a){
        this.amount = a;
    }

    @Override
    public String toString(){
        return Integer.toString(this.amount) + " " + this.ingredient;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof FridgeItem){
            FridgeItem f = (FridgeItem)o;
            if(f.ingredient.equals(this.ingredient) && f.amount == this.amount)
                return true;
        }
        return false;
    }
}
