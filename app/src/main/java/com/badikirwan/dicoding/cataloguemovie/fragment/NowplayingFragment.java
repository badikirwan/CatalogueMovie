package com.badikirwan.dicoding.cataloguemovie.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NowplayingFragment extends Fragment {

    private static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.API_KEY;
    private ArrayList<MovieModel> lisMovies;
    private MovieAdapter adapter;
    @BindView(R.id.recycler_view) RecyclerView rvMovie;

    public NowplayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nowplaying, container, false);
        ButterKnife.bind(this, view);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        lisMovies = new ArrayList<>();
        //load data from api
        loadData();

        return view;
    }

    private void loadData() {

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

                adapter = new MovieAdapter(getActivity(), lisMovies);
                rvMovie.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
