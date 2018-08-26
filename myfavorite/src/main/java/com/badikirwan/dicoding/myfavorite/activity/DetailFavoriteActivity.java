package com.badikirwan.dicoding.myfavorite.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.badikirwan.dicoding.myfavorite.R;
import com.badikirwan.dicoding.myfavorite.model.MovieModel;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFavoriteActivity extends AppCompatActivity {

    private MovieModel movie;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_overview_movie)
    TextView tvOvervie;

    @BindView(R.id.tv_release_movie)
    TextView tvRelease;

    @BindView(R.id.img_backdrop_movie)
    ImageView imgBackdrop;

    @BindView(R.id.tv_rating_movie)
    TextView tvRating;

    @BindView(R.id.tv_popular_movie)
    TextView tvPopuler;

    @BindView(R.id.tv_lang_movie)
    TextView tvLang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

        loadData();

        Objects.requireNonNull(getSupportActionBar()).setTitle(movie.getMovie_title());
        String release_date = movie.getMoview_release_date();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat new_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");

        try {
            Date date = date_format.parse(release_date);
            tvRelease.setText(new_format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvOvervie.setText(movie.getMovie_overview());
        tvRating.setText(movie.getMovie_vote());
        tvPopuler.setText(movie.getMovie_popularity());
        tvLang.setText(movie.getMovie_language());

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w154/" + movie.getMovie_backdrop())
                .crossFade()
                .into(imgBackdrop);
    }

    private void loadData() {
        Uri uri = getIntent().getData();
        if (uri == null) return;
        Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) movie = new MovieModel(cursor);
            cursor.close();
        }
    }
}
