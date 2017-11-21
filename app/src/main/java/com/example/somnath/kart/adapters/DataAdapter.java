package com.example.somnath.kart.adapters;

/**
 * Created by SOMNATH on 28-10-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.somnath.kart.ItemDetails;
import com.example.somnath.kart.R;
import com.example.somnath.kart.product_category.Bags;
import com.example.somnath.kart.product_category.Children;
import com.example.somnath.kart.product_category.Jewellery;
import com.example.somnath.kart.product_category.Men;
import com.example.somnath.kart.product_category.Mobile;
import com.example.somnath.kart.product_category.Mobile_Accessories;
import com.example.somnath.kart.product_category.Sports;
import com.example.somnath.kart.product_category.TvAudioVideo;
import com.example.somnath.kart.product_category.Women;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private String [] jsonArr;
    private Context mContext;
    private  String[] mIm;
    private String st;


    public DataAdapter(Context context , String [] json ,String []imageid,String string)
        {  jsonArr=json;
          mContext = context;
            mIm=imageid;
            st=string;


        }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView,mTextView2;
        public RelativeLayout mLinearLayout,mLinearLayout2;
        public ImageView imageView,imageView2;
        public ViewHolder(View v){
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv);
            mLinearLayout=(RelativeLayout) v.findViewById(R.id.relblock);
            imageView=(ImageView) v.findViewById(R.id.block);




        }
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.category_block,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mTextView.setText(jsonArr[position]);

            Picasso.with(mContext)
                    .load(mIm[position])
                    .resize(200,200)
                    .into(holder.imageView);

        if(st=="home")
        {
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pricestring=jsonArr[position];
                    String image=mIm[position];
                    Intent intent=new Intent(mContext,ItemDetails.class);
                    intent.putExtra("a",image);
                    intent.putExtra("b",pricestring);
                     mContext.startActivity(intent);

                }
            });

        }

        if(st=="fashion"){
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       switch (holder.getAdapterPosition()){
                           case  0:
                               startFragment(new Men());
                               break;
                           case 1:
                               startFragment(new Women());
                               break;
                           case 2:
                               startFragment(new Children());
                           case 3:
                               startFragment(new Sports());
                           case 4:
                               startFragment(new Jewellery());
                           case 5:
                               startFragment(new Bags());
                       }
                      }
                   }
               );
              }
              else
        if(st=="mobile")
        {   holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (holder.getAdapterPosition()){
                    case  0:
                         startFragment(new Mobile());
                        break;
                    case 1:
                        startFragment(new Mobile_Accessories());
                        break;
                    case 2:
                        startFragment(new TvAudioVideo());
                        break;
                }
            }});


        }
              else
              if(st=="home")
              {


              }
              else
              {

              }
    }


    @Override
    public int getItemCount() {
        return mIm.length;
    }

    private void startFragment(Fragment fragment)
    {
        FragmentActivity fragmentActivity = (FragmentActivity) mContext;
        FragmentManager currentFragment =fragmentActivity.getSupportFragmentManager();

        currentFragment.beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
    }


}