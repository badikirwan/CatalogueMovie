package com.badikirwan.dicoding.myfavorite.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_MOVIE = "myfavorite";

    public static final class FavoriteColumn implements BaseColumns {
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String RELEASE_DATE = "date";
        public static String POSTER = "poster";
    }

    public static final String AUTHORITY = "com.badikirwan.caloguemovieapp";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
