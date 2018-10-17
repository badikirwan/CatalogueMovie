package com.badikirwan.dicoding.cataloguemovie.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Cursor list;

    public FavoriteAdapter(Cursor items) {
        replaceAll(items);
    }

    public void replaceAll(Cursor items) {
        list = items;
        notifyDataSetChanged();
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_movie, parent, false);
        return new FavoriteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }

        return list.getCount();
    }

    private MovieModel getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }

        return new MovieModel(list);
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

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

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final MovieModel item) {
            title_movie.setText(item.getMovie_title());
            overview_movie.setText(item.getMovie_overview());
            date_movie.setText(item.getMoview_release_date());
            Glide.with(itemView.getContext())
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
                    Toast.makeText(itemView.getContext(), "Share Movie", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
