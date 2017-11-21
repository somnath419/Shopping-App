package com.example.somnath.kart.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.somnath.kart.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SlidingImageAdapter extends PagerAdapter {
   private String [] strings;
   private Context mContext;
   private LayoutInflater mLayoutInflater;

    public SlidingImageAdapter(Context context ,String [] st ) {
       mContext = context;
       strings=st;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);


        Picasso.with(mContext).load(strings[position]).placeholder(R.drawable.roundround).resize(400,380).into(imageView);
        container.addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}