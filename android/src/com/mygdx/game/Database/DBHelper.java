package com.mygdx.game.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Duy Tang on 10/28/2017.
 */

public class DBHelper {
    private static AssetManager asm;
    private static WordListDB db;
    private static String file_name = "words.txt";
    private static final String SHARED_PREFERENCES_PROFILE = "com.mygdx.game.PREFERENCE_DB_EXIST_CHECKER";
    private static final String key = "existed";
    private static final String check_querry = "SELECT * from " + WordListDB.WordListSchema.FeedEntry.TABLE_NAME +
            " WHERE " + WordListDB.WordListSchema.FeedEntry.COLUMN_NAME_TITLE + "=?;";
    private static final String get_querry = "SELECT " + WordListDB.WordListSchema.FeedEntry.COLUMN_NAME_TITLE
            + " FROM " + WordListDB.WordListSchema.FeedEntry.TABLE_NAME +
            " WHERE " + WordListDB.WordListSchema.FeedEntry.COLUMN_NAME_SUBTITLE + "=? ORDER BY RANDOM() LIMIT 1;" ;

    public static void writeData(Context context, int lowerBound, int upperBound) {

        SharedPreferences sharedPref = context.getSharedPreferences(
                SHARED_PREFERENCES_PROFILE, Context.MODE_PRIVATE);
        /*delete the table
        SharedPreferences.Editor edito = sharedPref.edit();
        edito.putBoolean(key, false);
        edito.commit();
        context.deleteDatabase(WordListDB.DATABASE_NAME);*/

        //check if the database has been written
        if (!sharedPref.getBoolean(key, false)) {
            //read the database
            db = WordListDB.getInstance(context);
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            asm = context.getResources().getAssets();
            try {
                InputStreamReader isr = new InputStreamReader(asm.open(file_name));
                BufferedReader buf = new BufferedReader(isr);
                String str;
                while ((str = buf.readLine()) != null ) {
                    if (str.length() < lowerBound || str.length() > upperBound){
                        continue;
                    }
                    Cursor cur = sqLiteDatabase.rawQuery(check_querry, new String[]{str});
                    if (cur.getCount() == 0) { // if word does not exist then add
                        cur.close();
                        ContentValues values = new ContentValues();
                        values.put(WordListDB.WordListSchema.FeedEntry.COLUMN_NAME_TITLE, str);
                        values.put(WordListDB.WordListSchema.FeedEntry.COLUMN_NAME_SUBTITLE, str.length());
                        long id;
                        if ((id=sqLiteDatabase.insert(WordListDB.WordListSchema.FeedEntry.TABLE_NAME,
                                null, values)) == -1) {
                            Log.d("Status sql read", "read not successful");
                        } else {
                            Log.d("Status sql read", "read successful "+id);
                        }
                    }
                }
                isr.close();
                //mark that the database exists
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(key, true);
                editor.commit();
            } catch (Exception e) {
                Log.d("Error",e.getMessage());
            }

        }

    }

    public static String getAWord(Context context, int length, int lower, int higher) {


        String theword = "";
         SharedPreferences sharedPref = context.getSharedPreferences(
                SHARED_PREFERENCES_PROFILE, Context.MODE_PRIVATE);

        try {
            if (!sharedPref.getBoolean(key, false)) { //if the database does not exist
                writeData(context, lower, higher);
            }
            db = WordListDB.getInstance(context);
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
            Cursor cur = sqLiteDatabase.rawQuery(get_querry, new String[]{String.valueOf(length)});
           // String[] listOfCandidates=new String[cur.getCount()];
            Log.d("Number of candidates",cur.getCount()+"");
            cur.moveToFirst();
            theword =cur.getString(0);
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        return theword;
    }

}
