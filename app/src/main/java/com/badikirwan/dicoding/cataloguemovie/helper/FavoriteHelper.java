package com.badikirwan.dicoding.cataloguemovie.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.ID;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.TABLE_MOVIE;

public class FavoriteHelper {

    private Context context;
    private DatabaseHelper dbhelper;
    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dbhelper = new DatabaseHelper(context);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbhelper.close();
    }

    public Cursor queryProvider() {
        return database.query(
                TABLE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                ID + " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(
                TABLE_MOVIE,
                null,
                ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long insertProvider(ContentValues values) {
        return database.insert(
                TABLE_MOVIE,
                null,
                values
        );
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(
                TABLE_MOVIE,
                values,
                ID + " = ?",
                new String[]{id}
        );
    }

    public int deleteProvider(String id) {
        return database.delete(
                TABLE_MOVIE,
                ID + " = ?",
                new String[]{id}
        );
    }
}
