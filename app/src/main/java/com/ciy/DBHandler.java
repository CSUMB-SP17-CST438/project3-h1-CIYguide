package com.ciy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/*
 * Author: Marilyn Florek
 * Date: 04/27/17
 */
/*
    This is where my SQLite Database is implemented.
 */
public class DBHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDatabase.db";

    //Previous Searches Table
    private static final String PREVIOUS_SEARCH_TABLE = "Previous";
    private static final String PREVIOUS_SEARCH_ID = "_id";
    public static final int USERS_ID_COL = 0;
    private static final String PREVIOUS_USER_NAME = "UserName";
    public static final int PREVIOUS_USER_NAME_COL = 1;
    private static final String PREVIOUS_SEARCHES = "FirstSearch";
    public static final int PREVIOUS_SEARCHES_COL = 2;


    //My Fridge Table
    private static final String FRIDGE_TABLE = "Fridge";
    private static final String FRIDGE_ID = "_id";
    public static final int FRIDGE_ID_COL = 0;
    private static final String FRIDGE_USER_NAME = "UserName";
    public static final int FRIDGE_USER_NAME_COL = 1;
    private static final String FRIDGE_ITEM = "Ingredients";
    public static final int FRIDGE_ITEM_COL = 2;

    //Saved Recipes
    private static final String SAVED_TABLE = "Saved";
    private static final String SAVED_ID = "_id";
    public static final int SAVED_ID_COL = 0;
    private static final String SAVED_USER_NAME = "UserName";
    public static final int SAVED_USER_NAME_COL = 1;
    private static final String RECIPE_DATA = "Recipe";
    public static final int RECIPE_DATA_COL = 2;

    // TO BE ADDED LATER
//    //Recipes the User Created Table
//    private static final String CREATED_TABLE = "Created";
//    private static final String CREATED_ID = "_id";
//    public static final int CREATED_ID_COL = 0;


    public static final String CREATE_PREVIOUS_TABLE = "CREATE TABLE " + PREVIOUS_SEARCH_TABLE + "(" +
            PREVIOUS_SEARCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PREVIOUS_USER_NAME + " TEXT," +
            PREVIOUS_SEARCHES + " TEXT)";

    public static final String CREATE_FRIDGE_TABLE = "CREATE TABLE " + FRIDGE_TABLE + "(" +
            FRIDGE_USER_NAME + " TEXT," +
            FRIDGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FRIDGE_ITEM + " TEXT)";

    public static final String CREATE_SAVED_TABLE = "CREATE TABLE " + SAVED_TABLE + "(" +
            SAVED_USER_NAME + " TEXT," +
            SAVED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RECIPE_DATA + " TEXT)";

    public static final String DROP_PREVIOUS_TABLE = "DROP TABLE IF EXISTS " + PREVIOUS_SEARCH_TABLE;
    public static final String DROP_FRIDGE_TABLE = "DROP TABLE IF EXISTS " + FRIDGE_TABLE;
    public static final String DROP_SAVED_TABLE = "DROP TABLE IF EXISTS " + SAVED_TABLE;


    /*
        All of the very important methods for the Database creation begin here
     */

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PREVIOUS_TABLE);
            db.execSQL(CREATE_FRIDGE_TABLE);
            db.execSQL(CREATE_SAVED_TABLE);

//            db.execSQL("INSERT INTO Users VALUES (1, '!admin2', '!admin2')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d("Upgrade", "Upgrading db from version " + oldVersion + "to " + newVersion);
            db.execSQL(DBHandler.DROP_FRIDGE_TABLE);
            db.execSQL(DBHandler.DROP_PREVIOUS_TABLE);
            db.execSQL(DBHandler.DROP_SAVED_TABLE);

            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    /*
        These 4 methods will make it possible to access the database in the rest of the app
     */
    public DBHandler(Context context) {
        dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    /*
       This is where the Database Data creating begins
     */

    //Insert Data
    public void storeSearch(String username, ArrayList<String> searchphrases){
        openWritableDB();
        ContentValues values = new ContentValues();
        String ingredientList = "";

        for(int j = 0; j < searchphrases.size(); j++){
            //trim space at edges and replace spaces in middle with +
            //to query better : lorenzo


            String adjusted = searchphrases.get(j).trim();
            adjusted = adjusted.replace("\\s+", "+");
            if(j == searchphrases.size()-1)
                ingredientList += searchphrases.get(j);
            else
                ingredientList += searchphrases.get(j) + ",";
        }

        values.put(PREVIOUS_USER_NAME, username);
        values.put(PREVIOUS_SEARCHES, ingredientList);
        db.insert(PREVIOUS_SEARCH_TABLE, null, values);
        closeDB();
    }

    public void storeFridge(String username, String ingredientList){
        openWritableDB();
        ContentValues values = new ContentValues();
        values.put(FRIDGE_USER_NAME, username);
        values.put(FRIDGE_ITEM, ingredientList);
        db.insert(FRIDGE_TABLE, null, values);
        closeDB();
    }

    public void saveNewRecipe(String username, String recipeJson){
        openWritableDB();
        ContentValues values = new ContentValues();
        values.put(SAVED_USER_NAME, username);
        values.put(RECIPE_DATA, recipeJson);
        db.insert(SAVED_TABLE, null, values);
        closeDB();
    }

    //Get Data
    public ArrayList<String> getPreviousSearches(String userName){
        ArrayList<String> searchphrases = new ArrayList<String>();

        String where = PREVIOUS_SEARCH_ID + "= ?";
        String[] whereArgs = {userName};
        this.openReadableDB();

        Cursor cursor = db.query(PREVIOUS_SEARCH_TABLE, null, where, whereArgs, null, null, null);
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            String getData = cursor.getString(PREVIOUS_SEARCHES_COL);
            searchphrases.add(getData);
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return searchphrases;
    }

    public ArrayList<String> getStoredRecipes(String userName){
        ArrayList<String> searchphrases = new ArrayList<String>();

        String where = SAVED_ID + "= ?";
        String[] whereArgs = {userName};
        this.openReadableDB();

        Cursor cursor = db.query(SAVED_TABLE, null, where, whereArgs, null, null, null);
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            String getData = cursor.getString(RECIPE_DATA_COL);
            searchphrases.add(getData);
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return searchphrases;
    }

    public ArrayList<String> getFridgeItems(String userName){
        ArrayList<String> searchphrases = new ArrayList<String>();

        String where = FRIDGE_ID + "= ?";
        String[] whereArgs = {userName};
        this.openReadableDB();

        Cursor cursor = db.query(FRIDGE_TABLE, null, where, whereArgs, null, null, null);
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            String getData = cursor.getString(FRIDGE_ITEM_COL);
            searchphrases.add(getData);
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return searchphrases;
    }

}