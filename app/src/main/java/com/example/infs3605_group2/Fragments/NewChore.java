package com.example.infs3605_group2.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infs3605_group2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewChore extends Fragment {


    public NewChore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_chore, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {

    }

}
