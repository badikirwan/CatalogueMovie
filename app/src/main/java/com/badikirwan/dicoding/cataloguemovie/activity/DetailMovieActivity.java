package com.badikirwan.dicoding.cataloguemovie.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.badikirwan.dicoding.cataloguemovie.BuildConfig;
import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.helper.FavoriteHelper;
import com.badikirwan.dicoding.cataloguemovie.model.MovieModel;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.CONTENT_URI;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.BACKDROP;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.DESCRIPTION;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.ID;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.LANG;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.POPULARITY;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.POSTER;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.RATING;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.RELEASE_DATE;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.FavoriteColumn.TITLE;

public class DetailMovieActivity extends AppCompatActivity {

    public static String MOVIE_DETAIL = "movie_detail";
    private boolean isFavorite = false;
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

    @BindView(R.id.iv_fav)
    FloatingActionButton btnFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));


        movie = getIntent().getParcelableExtra(MOVIE_DETAIL);

        LoadData();


        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    FavoriteRemove();
                    Toast.makeText(DetailMovieActivity.this, "Berhasil Dihapus", Toast.LENGTH_LONG).show();
                } else {
                    FavoriteSave();
                    Toast.makeText(DetailMovieActivity.this, "Berhasil Difavoritkan", Toast.LENGTH_LONG).show();
                }

                isFavorite = !isFavorite;
                setFavorite();

            }
        });

    }

    private void LoadData() {
        LoadDataSQLite();

        Objects.requireNonNull(getSupportActionBar()).setTitle(movie.getMovie_title());
        int id = movie.getMovie_id();
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
                .load(BuildConfig.POSTER_URL + movie.getMovie_backdrop())
                .into(imgBackdrop);
    }

    private void LoadDataSQLite() {
        FavoriteHelper favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + movie.getMovie_id()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                isFavorite = true;
            }

            cursor.close();
        }

        setFavorite();
    }

    private void setFavorite() {
        if (isFavorite) {
            btnFav.setImageResource(R.drawable.ic_favorite_black);
        } else {
            btnFav.setImageResource(R.drawable.ic_favorite);
        }
    }

    private void FavoriteSave() {
        ContentValues values = new ContentValues();
        values.put(ID, movie.getMovie_id());
        values.put(TITLE, movie.getMovie_title());
        values.put(DESCRIPTION, movie.getMovie_overview());
        values.put(RELEASE_DATE, movie.getMoview_release_date());
        values.put(POSTER, movie.getMovie_poter());
        values.put(BACKDROP, movie.getMovie_backdrop());
        values.put(RATING, movie.getMovie_vote());
        values.put(POPULARITY, movie.getMovie_popularity());
        values.put(LANG, movie.getMovie_language());

        getContentResolver().insert(CONTENT_URI, values);
    }

    private void FavoriteRemove() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + movie.getMovie_id()),
                null,
                null
        );
    }

}
