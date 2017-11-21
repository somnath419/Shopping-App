package com.example.somnath.kart;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.somnath.kart.adapters.CustomGrid;
import com.squareup.picasso.Picasso;

/**
 * Created by SOMNATH on 17-11-2017.
 */

public class ItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetails);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaritem);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView= (ImageView) findViewById(R.id.itemphoto);

        String strtext =getIntent().getStringExtra("a");
        String string=getIntent().getStringExtra("b");

        Picasso.with(this)
                .load(strtext)
                .into(imageView);




    }
    @Override
    public void onResume() {
        super.onResume();

    }
}

