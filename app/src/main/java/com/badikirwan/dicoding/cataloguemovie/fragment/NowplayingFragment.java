package com.badikirwan.dicoding.cataloguemovie.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badikirwan.dicoding.cataloguemovie.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowplayingFragment extends Fragment {


    public NowplayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nowplaying, container, false);
    }

}
