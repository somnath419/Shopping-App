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
    GridView grid;
    String[] web = {
            "Its different",
            "Its too ",
            "And Yet Again",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    };
    int[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.gridlayout, container, false);
        CustomGrid adapter = new CustomGrid(getActivity(), web, imageId);
        grid = (GridView) v.findViewById(R.id.grid00);
        grid.setAdapter(adapter);

        new Women().setGridViewHeightBasedOnChildren(grid,2);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(new SessionManager(getActivity()).checkLogin())
                    Toast.makeText(getActivity(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "You first Login man click on account button", Toast.LENGTH_SHORT).show();


            }
        });

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
