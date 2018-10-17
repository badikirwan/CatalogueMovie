package com.badikirwan.dicoding.cataloguemovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.fragment.SettingsFragment;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction()
                .replace(R.id.setting_content, new SettingsFragment()).commit();

        getSupportActionBar().setTitle(R.string.title_activity_settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
