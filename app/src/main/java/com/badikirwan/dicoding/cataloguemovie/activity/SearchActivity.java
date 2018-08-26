package com.badikirwan.dicoding.cataloguemovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.badikirwan.dicoding.cataloguemovie.BuildConfig;
import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.adapter.MovieAdapter;
import com.badikirwan.dicoding.cataloguemovie.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<MovieModel> lisMovies;
    @BindView(R.id.lst_search) RecyclerView rvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        lisMovies = new ArrayList<>();

        String keyword = getIntent().getStringExtra("keyword");
        SearchMovie(keyword);
    }

    void SearchMovie(String query) {

        final String API_URL = BuildConfig.API_MAIN + BuildConfig.API_KEY + "&language=en-US&query="+ query;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        MovieModel movie = new MovieModel();
                        movie.setMovie_title(data.getString("title"));
                        movie.setMovie_overview(data.getString("overview"));
                        movie.setMoview_release_date(data.getString("release_date"));
                        movie.setMovie_poter(data.getString("poster_path"));
                        movie.setMovie_backdrop(data.getString("backdrop_path"));
                        lisMovies.add(movie);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MovieAdapter adapter = new MovieAdapter(getApplicationContext());
                adapter.setListMovie(lisMovies);
                rvMovie.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
