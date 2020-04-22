package com.example.infs3605_group2.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.infs3605_group2.Models.TabAdapter;
import com.example.infs3605_group2.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildLearningMain extends Fragment {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ChildLearningMain() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_learning_view, container, false);
    }

    public void onResume() {
        super.onResume();
        viewPager = getView().findViewById(R.id.viewPager);
        tabLayout = getView().findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(new YoutubeRecycler(), "YouTube");
        adapter.addFragment(new ChildGlossary(), "Glossary");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();

    }

    public void onPause() {
        super.onPause();
        int count = adapter.getCount();
        for (int i =0;i<1;i++) {
            adapter.destroyItem(viewPager, i, adapter.getItem(i));
            tabLayout.removeTabAt(i);
        }
        tabLayout.invalidate();
        adapter.notifyDataSetChanged();
    }

}
