package com.badikirwan.dicoding.myfavorite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.BACKDROP;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.DESCRIPTION;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.ID;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.LANG;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.POPULARITY;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.POSTER;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.RATING;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.RELEASE_DATE;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.TITLE;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.TABLE_MOVIE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "catalogue_movie";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAVORITE = "create table "+TABLE_MOVIE+
            " ("+ID+" integer primary key autoincrement, " +
            TITLE+" text not null, " +
            DESCRIPTION+ " text not null, " +
            RELEASE_DATE+ " text not null, " +
            BACKDROP+ " text not null, " +
            POSTER+ " text not null, " +
            RATING+ " text not null, " +
            POPULARITY+ " text not null, " +
            LANG+ " text not null);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }
}
