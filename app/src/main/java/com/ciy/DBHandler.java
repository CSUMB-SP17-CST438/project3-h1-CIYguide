package com.ciy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CheckBox;

import com.ciy.PreferencesDBSchema.Preferences;

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
}
