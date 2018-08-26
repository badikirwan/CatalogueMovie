package com.badikirwan.dicoding.cataloguemovie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.badikirwan.dicoding.cataloguemovie.BuildConfig;
import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.activity.DetailMovieActivity;
import com.badikirwan.dicoding.cataloguemovie.model.MovieModel;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<MovieModel> listMovie = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setListMovie(List<MovieModel> item) {
        this.listMovie = item;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(listMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie_title)
        TextView title_movie;

        @BindView(R.id.tv_movie_overview)
        TextView overview_movie;

        @BindView(R.id.tv_movie_date)
        TextView date_movie;

        @BindView(R.id.img_movie_poster)
        ImageView poster_movie;

        @BindView(R.id.btn_detail_movie)
        Button btn_detail;

        @BindView(R.id.btn_share_movie)
        Button btn_share;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final MovieModel item) {
            title_movie.setText(item.getMovie_title());
            overview_movie.setText(item.getMovie_overview());

            String release_date = item.getMoview_release_date();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");

            try {
                Date date = date_format.parse(release_date);
                String date_release = new_date_format.format(date);
                date_movie.setText(date_release);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(context)
                    .load(BuildConfig.POSTER_URL + item.getMovie_poter())
                    .into(poster_movie);

            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.MOVIE_DETAIL, item);
                    itemView.getContext().startActivity(intent);
                }
            });

            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Share movie", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
