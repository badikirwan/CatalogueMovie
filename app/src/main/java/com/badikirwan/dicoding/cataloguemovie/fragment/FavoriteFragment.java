package com.badikirwan.dicoding.cataloguemovie.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.adapter.FavoriteAdapter;
import com.badikirwan.dicoding.cataloguemovie.helper.FavoriteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private Cursor list;
    private FavoriteHelper favoriteHelper;
    private FavoriteAdapter adapter;
    private Context context;

    @BindView(R.id.recycler_view)
    RecyclerView rvFavorite;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        context = view.getContext();
        ButterKnife.bind(this, view);

        adapter = new FavoriteAdapter(list);
        rvFavorite.setLayoutManager(new LinearLayoutManager(context));
        rvFavorite.setAdapter(adapter);

        new LoadData().execute();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null){
            favoriteHelper.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.d("background", "running");
            return context.getContentResolver().query(
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
            adapter.replaceAll(list);
        }
    }


}
