package com.badikirwan.dicoding.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.badikirwan.dicoding.cataloguemovie.CustomOnItemClickListener;
import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.activity.DetailMovieActivity;
import com.badikirwan.dicoding.cataloguemovie.model.MovieModel;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private ArrayList<MovieModel> listMovie;
    private Context context;

    public MovieAdapter(Context context, ArrayList<MovieModel> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final MovieModel movie = listMovie.get(position);

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500/" + movie.getMovie_poter())
                .into(holder.poster_movie);

        String release_date = movie.getMoview_release_date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");

        try {
            Date date = date_format.parse(release_date);
            String date_release = new_date_format.format(date);
            holder.date_movie.setText(date_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.title_movie.setText(movie.getMovie_title());
        holder.overview_movie.setText(movie.getMovie_overview());

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btn_detail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
           @Override
           public void onItemClicked(View view, int position) {
               Intent intent = new Intent(context, DetailMovieActivity.class);
               intent.putExtra(DetailMovieActivity.EXTRA_TITLE, movie.getMovie_title());
               intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, movie.getMovie_overview());
               intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE, movie.getMoview_release_date());
               intent.putExtra(DetailMovieActivity.EXTRA_IMG_BACKDROP, movie.getMovie_backdrop());
               intent.putExtra(DetailMovieActivity.EXTRA_IMG_POSTER, movie.getMovie_poter());
               context.startActivity(intent);
           }
        }));
        holder.btn_share.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share movie", Toast.LENGTH_SHORT).show();
            }
        }));

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie_title) TextView title_movie;
        @BindView(R.id.tv_movie_overview) TextView overview_movie;
        @BindView(R.id.tv_movie_date) TextView date_movie;
        @BindView(R.id.img_movie_poster) ImageView poster_movie;
        @BindView(R.id.btn_detail_movie) Button btn_detail;
        @BindView(R.id.btn_share_movie) Button btn_share;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
