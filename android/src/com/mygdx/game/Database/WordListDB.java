package com.mygdx.game.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Duy Tang on 10/28/2017.
 */

public class WordListDB extends SQLiteOpenHelper {
    public final class WordListSchema {
        private WordListSchema() {
        }

        public class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "WordList";
            public static final String COLUMN_NAME_TITLE = "word";
            public static final String COLUMN_NAME_SUBTITLE = "length";
        }

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                        FeedEntry.COLUMN_NAME_SUBTITLE + " INTEGER)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    }
    private static WordListDB sqlInstance=null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WordList.db";

    private WordListDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized WordListDB getInstance(Context context) {
        if (sqlInstance == null) {
            sqlInstance = new WordListDB(context.getApplicationContext());
        }
        return sqlInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WordListSchema.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(WordListSchema.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
