package com.badikirwan.dicoding.myfavorite.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.badikirwan.dicoding.myfavorite.R;
import com.badikirwan.dicoding.myfavorite.adapter.FavoriteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    private Cursor list;
    private FavoriteAdapter adapter;

    @BindView(R.id.rv_movie)
    RecyclerView rvFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new FavoriteAdapter(list);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setAdapter(adapter);

        new LoadData().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.d("background", "running");
            return getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            list = cursor;
            adapter.setListFavorite(list);
        }
    }
}
