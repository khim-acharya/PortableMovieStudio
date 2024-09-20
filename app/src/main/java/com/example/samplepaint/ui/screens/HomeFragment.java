package com.example.samplepaint.ui.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samplepaint.R;
import com.example.samplepaint.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding homeFragmentBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeFragmentBinding = HomeFragmentBinding.bind(inflater.inflate(R.layout.home_fragment, container, false));
        return homeFragmentBinding.getRoot();
    }
}