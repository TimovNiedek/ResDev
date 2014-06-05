package com.tsjd.HotMeals;

import com.example.tabtest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeTab extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);
        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText("Welcome to the Hotmeals App!");
        return v;
    }
}