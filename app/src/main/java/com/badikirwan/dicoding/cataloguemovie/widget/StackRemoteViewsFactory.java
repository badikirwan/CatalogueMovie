package com.badikirwan.dicoding.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.badikirwan.dicoding.cataloguemovie.BuildConfig;
import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.helper.DatabaseHelper;
import com.badikirwan.dicoding.cataloguemovie.model.MovieModel;
import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.android.volley.VolleyLog.TAG;
import static com.badikirwan.dicoding.cataloguemovie.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor list;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        this.mContext = applicationContext;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (list != null) {
            list.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        list = mContext.getContentResolver().query(CONTENT_URI,
                null,
                null,
                null,
                null);

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (list != null) {
            list.close();
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MovieModel item = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;

        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.POSTER_URL + item.getMovie_poter())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "getViewAt: " + item.getMovie_title());

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(MovieWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return list.moveToPosition(position) ? list.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private MovieModel getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }

        return new MovieModel(list);
    }
}
