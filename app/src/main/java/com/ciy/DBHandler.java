package com.ciy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CheckBox;

import com.ciy.PreferencesDBSchema.Preferences;
import com.ciy.PrevDBSchema.Prev;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Joe Otter on 5/19/2017.
 */

public class DBHandler {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipes.db";

    private static class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqldb){
            sqldb.execSQL("CREATE TABLE IF NOT EXISTS " + Preferences.NAME + "("
                    + Preferences.Cols.PREFNAME + " TEXT NOT NULL, " +
                    Preferences.Cols.CHECKED + " TEXT NOT NULL);"
            );
            sqldb.execSQL("CREATE TABLE IF NOT EXISTS " + Prev.NAME + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Prev.Cols.R_NAME + " TEXT NOT NULL, " +
                    Prev.Cols.R_IMG + " TEXT NOT NULL, " +
                    Prev.Cols.R_URL + " TEXT NOT NULL, " +
                    Prev.Cols.R_INGREDIENTS + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqldb, int i, int i1){
            sqldb.execSQL("DROP TABLE IF EXISTS " + Preferences.NAME + ";");
            onCreate(sqldb);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    //access database in rest of app
    public DBHandler(Context context){
        dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void readDB(){db = dbHelper.getReadableDatabase();}
    private void writeDB(){db = dbHelper.getWritableDatabase();}
    private void closeDB(){
        if(db != null)
            db.close();
    }


    //////////////////////////////////////////////////////////////////////////////
    //              PREFERENCES DATABASE AND ITS FUNCTIONS
    //////////////////////////////////////////////////////////////////////////////
    //if table is not created, create table
    private void initPREFS(){
        writeDB();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Preferences.NAME + "("
                + Preferences.Cols.PREFNAME + " TEXT NOT NULL, " +
                Preferences.Cols.CHECKED + " TEXT NOT NULL);"
        );
        closeDB();
        readDB();
        Cursor c = db.rawQuery("SELECT * FROM " + Preferences.NAME + ";", null);
        if(c.getCount() < 1){
            closeDB();
            writeDB();
            db.execSQL("INSERT INTO " + Preferences.NAME + "(" +
                    Preferences.Cols.PREFNAME + "," + Preferences.Cols.CHECKED +
                    ") VALUES ('balanced','n'),('high-protein','n'),('high-fiber','n')," +
                    "('low-fat','n'),('low-carb','n'),('low-sodium','n'),('vegan','n')," +
                    "('vegetarian','n'),('paleo','n'),('dairy-free','n'),('gluten-free','n')," +
                    "('wheat-free','n'),('fat-free','n'),('low-sugar','n'),('egg-free','n')," +
                    "('peanut-free','n'),('tree-nut-free','n'),('soy-free','n'),('fish-free','n')," +
                    "('shellfish-free','n');"
            );
            closeDB();
        }
        c.close();
        closeDB();
    }

    //update prefs
    public void updatePrefs(String rowVal, String checked){
        initPREFS();
        writeDB();
        db.execSQL("UPDATE " + Preferences.NAME + " SET " +
                Preferences.Cols.CHECKED + "='" + checked + "' WHERE " +
                Preferences.Cols.PREFNAME + "='" + rowVal + "';"
        );
        closeDB();
    }

    //get update prefs table
    public ArrayList<PrefEntry> getPrefs(){
        ArrayList<PrefEntry> entries = new ArrayList<PrefEntry>();

        initPREFS();
        readDB();
        Cursor c = db.rawQuery("SELECT * FROM " + Preferences.NAME + ";", null);
        if(c.moveToFirst()){
            do{
                PrefEntry p = new PrefEntry();
                p.setLabel(c.getString(0));
                p.setChecked(c.getString(1));
                entries.add(p);
            }while(c.moveToNext());
        }
        c.close();
        closeDB();

        return entries;
    }

    //get checkbox status from preferences database
    public boolean setChecked(String label, Context c){
        boolean checked = false;
        readDB();
        Cursor cu = db.rawQuery("SELECT * FROM " + Preferences.NAME +
                " WHERE " + Preferences.Cols.PREFNAME + "='" + label + "';", null
        );
        if(cu.getCount() > 0)
        {
            cu.moveToFirst();
            if(cu.getString(1).equals("y"))
                checked = true;
            else
                checked = false;
        }
        cu.close();
        closeDB();
        return checked;
    }

    //get checked preferences and return for search query
    public ArrayList<PrefEntry> getChecked(){
        ArrayList<PrefEntry> pe = new ArrayList<PrefEntry>();
        readDB();
        Cursor c = db.rawQuery("SELECT * FROM " + Preferences.NAME +
                " WHERE " + Preferences.Cols.CHECKED + "='y';", null);
        if(c.moveToFirst()){
            do {
                PrefEntry p = new PrefEntry();
                p.setChecked(c.getString(1));
                p.setLabel(c.getString(0));
                pe.add(p);
            }while(c.moveToNext());
        }
        c.close();
        closeDB();
        return pe;
    }


    //////////////////////////////////////////////////////////////////////////////
    //              Saved and Previous Recipes DATABASE AND ITS FUNCTIONS
    //////////////////////////////////////////////////////////////////////////////
    private void initSavePrev(){
        writeDB();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Prev.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Prev.Cols.R_NAME + " TEXT NOT NULL, " +
                Prev.Cols.R_IMG + " TEXT NOT NULL, " +
                Prev.Cols.R_URL + " TEXT NOT NULL, " +
                Prev.Cols.R_INGREDIENTS + " TEXT NOT NULL);"
        );
        closeDB();
    }

    //add entry to previous
    public void addToPrev(PreviousSaved PS){
        initSavePrev();
        writeDB();
        db.execSQL("INSERT INTO " + Prev.NAME + "(" +
                Prev.Cols.R_NAME + "," + Prev.Cols.R_IMG + "," +
                Prev.Cols.R_URL + "," + Prev.Cols.R_INGREDIENTS + ")" +
                "VALUES('" + PS.getName() + "','" + PS.getImage() + "','" +
                PS.getUrl() + "','" + PS.getStringIngredients() + "');"
        );
        closeDB();
    }

    //display all entries from previous recipes database table
    public ArrayList<PreviousSaved> getPrevEntries(){
        initSavePrev();
        ArrayList<PreviousSaved> all = new ArrayList<PreviousSaved>();

        readDB();
        Cursor c = db.rawQuery("SELECT * FROM " + Prev.NAME + ";", null);
        if(c.moveToFirst()){
            do{
                PreviousSaved temp = new PreviousSaved();
                temp.setName(c.getString(1));
                temp.setUrl(c.getString(3));
                temp.setImage(c.getString(2));
                temp.setIngredients(c.getString(4));
                all.add(temp);
            }while(c.moveToNext());
        }
        closeDB();

        return all;
    }

    //function used for testing to clear out previous database
    public void clearPrev(){
        writeDB();
        db.execSQL("DROP TABLE IF EXISTS " + Prev.NAME + ";");
        closeDB();
        initSavePrev();
    }


}
