package com.badikirwan.dicoding.myfavorite.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.BACKDROP;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.DESCRIPTION;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.ID;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.LANG;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.POPULARITY;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.POSTER;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.RATING;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.RELEASE_DATE;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.FavoriteColumn.TITLE;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.getColumnInt;
import static com.badikirwan.dicoding.myfavorite.db.DatabaseContract.getColumnString;


public class MovieModel implements Parcelable {

    private int movie_id;
    private String movie_title;
    private String movie_poter;
    private String movie_backdrop;
    private String movie_overview;
    private String moview_release_date;
    private String movie_vote;
    private String movie_popularity;
    private String movie_language;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_poter() {
        return movie_poter;
    }

    public void setMovie_poter(String movie_poter) {
        this.movie_poter = movie_poter;
    }

    public String getMovie_backdrop() {
        return movie_backdrop;
    }

    public void setMovie_backdrop(String movie_backdrop) {
        this.movie_backdrop = movie_backdrop;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        this.movie_overview = movie_overview;
    }

    public String getMoview_release_date() {
        return moview_release_date;
    }

    public void setMoview_release_date(String moview_release_date) {
        this.moview_release_date = moview_release_date;
    }

    public String getMovie_vote() {
        return movie_vote;
    }

    public void setMovie_vote(String movie_vote) {
        this.movie_vote = movie_vote;
    }

    public String getMovie_popularity() {
        return movie_popularity;
    }

    public void setMovie_popularity(String movie_popularity) {
        this.movie_popularity = movie_popularity;
    }

    public String getMovie_language() {
        return movie_language;
    }

    public void setMovie_language(String movie_language) {
        this.movie_language = movie_language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movie_id);
        dest.writeString(this.movie_title);
        dest.writeString(this.movie_poter);
        dest.writeString(this.movie_backdrop);
        dest.writeString(this.movie_overview);
        dest.writeString(this.moview_release_date);
        dest.writeString(this.movie_vote);
        dest.writeString(this.movie_popularity);
        dest.writeString(this.movie_language);
    }

    public MovieModel() {
    }

    public MovieModel(Cursor cursor) {
        this.movie_id = getColumnInt(cursor, ID);
        this.movie_title = getColumnString(cursor, TITLE);
        this.movie_overview = getColumnString(cursor, DESCRIPTION);
        this.moview_release_date = getColumnString(cursor, RELEASE_DATE);
        this.movie_backdrop = getColumnString(cursor, BACKDROP);
        this.movie_poter = getColumnString(cursor, POSTER);
        this.movie_vote = getColumnString(cursor, RATING);
        this.movie_popularity = getColumnString(cursor, POPULARITY);
        this.movie_language = getColumnString(cursor, LANG);
    }

    private MovieModel(Parcel in) {
        this.movie_id = in.readInt();
        this.movie_title = in.readString();
        this.movie_poter = in.readString();
        this.movie_backdrop = in.readString();
        this.movie_overview = in.readString();
        this.moview_release_date = in.readString();
        this.movie_vote = in.readString();
        this.movie_popularity = in.readString();
        this.movie_language = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
