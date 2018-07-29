package com.badikirwan.dicoding.cataloguemovie.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.badikirwan.dicoding.cataloguemovie.BuildConfig;
import com.badikirwan.dicoding.cataloguemovie.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static String EXTRA_TITLE = "EXTRA_TITLE";
    public static String EXTRA_OVERVIEW = "EXTRA_OVERVIEW";
    public static String EXTRA_IMG_POSTER = "EXTRA_IMG_POSTER";
    public static String EXTRA_IMG_BACKDROP = "EXTRA_IMG_BACKDROP";
    public static String EXTRA_RELEASE_DATE = "EXTRA_RELEASE_DATE";

    @BindView(R.id.tv_title_movie) TextView tvTitle;
    @BindView(R.id.tv_overview_movie) TextView tvOvervie;
    @BindView(R.id.tv_release_movie) TextView tvRelease;
    @BindView(R.id.img_backdrop_movie) ImageView imgBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String backdrop = getIntent().getStringExtra(EXTRA_IMG_BACKDROP);

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

        tvTitle.setText(title);
        tvOvervie.setText(overview);

        Glide.with(this)
                .load(BuildConfig.POSTER_URL + backdrop)
                .crossFade()
                .into(imgBackdrop);
    }

}
