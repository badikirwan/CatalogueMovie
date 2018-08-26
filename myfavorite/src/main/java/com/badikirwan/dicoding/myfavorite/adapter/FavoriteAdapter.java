package com.badikirwan.dicoding.myfavorite.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.badikirwan.dicoding.myfavorite.activity.DetailFavoriteActivity;
import com.badikirwan.dicoding.myfavorite.R;
import com.badikirwan.dicoding.myfavorite.model.MovieModel;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Cursor listFavorite;

    public FavoriteAdapter(Cursor item) {
        setListFavorite(item);
    }

    public void setListFavorite(Cursor item) {
        this.listFavorite = item;
        notifyDataSetChanged();
    }
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        if (listFavorite == null) {
            return 0;
        } else {
            return listFavorite.getCount();
        }
    }

    private MovieModel getItem(int position) {
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new MovieModel(listFavorite);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie_title)
        TextView title_movie;

        @BindView(R.id.tv_movie_overview)
        TextView overview_movie;

        @BindView(R.id.tv_movie_date)
        TextView date_movie;

        @BindView(R.id.img_movie_poster)
        ImageView poster_movie;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final MovieModel items) {
            title_movie.setText(items.getMovie_title());
            overview_movie.setText(items.getMovie_overview());

            String release_date = items.getMoview_release_date();
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

            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w154/"+ items.getMovie_poter())
                    .into(poster_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailFavoriteActivity.class);
                    intent.setData(Uri.parse(CONTENT_URI + "/" + items.getMovie_id()));
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
