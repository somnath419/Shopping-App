package com.example.somnath.kart.product_category;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.somnath.kart.adapters.CustomGrid;
import com.example.somnath.kart.R;
import com.example.somnath.kart.SessionManager;

/**
 * Created by SOMNATH on 01-11-2017.
 */

public class Men extends Fragment {





        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            View v = inflater.inflate(R.layout.gridlayout, container, false);


            return v;

        }
        @Override
        public void onResume() {
            super.onResume();
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            ActionBar actionBar = activity.getSupportActionBar();
            actionBar.setTitle("Men");
        }

    }


