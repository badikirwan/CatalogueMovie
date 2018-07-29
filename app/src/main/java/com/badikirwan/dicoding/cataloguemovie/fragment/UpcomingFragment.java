package com.badikirwan.dicoding.cataloguemovie.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.adapter.MovieAdapter;
import com.badikirwan.dicoding.cataloguemovie.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    private static final String API = "https://api.themoviedb.org/3/movie/upcoming?api_key=33a4f2f91284c9133695dfba6bd802da&language=en-US";
    private ArrayList<MovieModel> lisMovies;
    private RecyclerView rvMovie;
    private MovieAdapter adapter;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nowplaying, container, false);
        rvMovie = view.findViewById(R.id.recycler_view);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        lisMovies = new ArrayList<>();
        //load data from api
        loadData();

        return view;
    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API, new Response.Listener<String>() {
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
                Log.d("ERROR :", error.toString());
                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
