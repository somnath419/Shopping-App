/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.somnath.kart;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.somnath.kart.adapters.DataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoryFragment extends Fragment {

    LinearLayout mRelativeLayout;
    private RecyclerView mRecyclerView,mRecyclerView2,mRecyclerView3,mRecyclerView4;
    private RecyclerView.Adapter mAdapter1,mAdapter2,mAdapter3,mAdapter4;
    private RecyclerView.LayoutManager mLayoutManager,mLayoutManager2,mLayoutManager3,mLayoutManager4;
    Bitmap bm[]=new Bitmap[3];
    private  ProgressDialog progressDialog ;
    private WebRequestReceiver receiver;
    private ProgressBar spinner;
    private TextView textView,textView2,textView3,textView4;
    private Button button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categories, container, false);
        spinner = (ProgressBar)v.findViewById(R.id.pbHeaderProgress);
        spinner.setVisibility(View.VISIBLE);


        return  v;
        }


    private void loadIntoListView(String json) throws JSONException {
       //creating a json array from the json string
      JSONArray jsonArray = new JSONArray(json);
        //creating a string array for listview

          String[] fashion = new String[jsonArray.length()];
          String[] elec = new String[jsonArray.length()];
          String[] fashionimagestr = new String[jsonArray.length()];
          String[] elecimagestr = new String[jsonArray.length()];

        //looping through all the elements in json array
          for (int i = 0; i < jsonArray.length(); i++) {
              //getting json object from the json array
              JSONObject obj = jsonArray.getJSONObject(i);
              //getting the name from the json object and putting it inside string array
              fashion[i] = obj.getString("fashion");
              elec[i] = obj.getString("electronics");
              fashionimagestr[i] = obj.getString("fashion_image");
              elecimagestr[i] = obj.getString("elec_image");
          }

          // Get the widgets reference from XML layout
          mRelativeLayout = (LinearLayout) getView().findViewById(R.id.rl);
          mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
          mRecyclerView2 = (RecyclerView) getView().findViewById(R.id.recycler_view2);
          mRecyclerView3=(RecyclerView) getView().findViewById(R.id.recycler_view3);
          mRecyclerView4=(RecyclerView) getView().findViewById(R.id.recycler_view4);

          mRecyclerView.setNestedScrollingEnabled(false);
          mRecyclerView2.setNestedScrollingEnabled(false);
          mRecyclerView3.setNestedScrollingEnabled(false);
          mRecyclerView4.setNestedScrollingEnabled(false);

          mRecyclerView.setHasFixedSize(true);
          mRecyclerView2.setHasFixedSize(true);
          mRecyclerView3.setHasFixedSize(true);
          mRecyclerView4.setHasFixedSize(true);

          // Define a layout for RecyclerView
          mLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
          mLayoutManager2 = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
          mLayoutManager3 = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
          mLayoutManager4 = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);

          mRecyclerView.setLayoutManager(mLayoutManager);
          mRecyclerView2.setLayoutManager(mLayoutManager2);
          mRecyclerView3.setLayoutManager(mLayoutManager3);
          mRecyclerView4.setLayoutManager(mLayoutManager4);

          // Initialize a new instance of RecyclerView Adapter instance
          mAdapter1 = new DataAdapter(getActivity(), fashion, fashionimagestr,"fashion");
          mAdapter2 = new DataAdapter(getActivity(), elec, elecimagestr,"mobile");
          mAdapter3 = new DataAdapter(getActivity(), elec, elecimagestr,"home");
          mAdapter4 = new DataAdapter(getActivity(), elec, elecimagestr,"daily");

          // Set the adapter for RecyclerView
          mRecyclerView.setAdapter(mAdapter1);
          mRecyclerView2.setAdapter(mAdapter2);
          mRecyclerView3.setAdapter(mAdapter3);
          mRecyclerView4.setAdapter(mAdapter4);
      }

        


    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(WebRequestReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new WebRequestReceiver();
        getActivity().registerReceiver(receiver, filter);

        Intent msgIntent = new Intent(getActivity(), WebRequestService.class);
        msgIntent.putExtra(WebRequestService.REQUEST_STRING, "https://improvised-lots.000webhostapp.com/getdata.php");
        getActivity().startService(msgIntent);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Category");
    }

    @Override
    public  void onPause()
    { super.onPause();
         getActivity().unregisterReceiver(receiver);
    }
    @Override
    public  void onDestroy()
    {super.onDestroy();
        Toast.makeText(getContext(), "Destroyed", Toast.LENGTH_SHORT).show();
    }

    public class WebRequestReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "package com.example.somnath.kart.intent.action.PROCESS_RESPONSE";
        @Override
        public void onReceive(Context context, Intent intent) {
            String reponseMessage = intent.getStringExtra(WebRequestService.RESPONSE_MESSAGE);
   if(reponseMessage!=null){
            try {   textView=(TextView) getView().findViewById(R.id.fashion);
                    textView2=(TextView) getView().findViewById(R.id.electronics);
                    textView3=(TextView) getView().findViewById(R.id.home);
                    textView4=(TextView) getView().findViewById(R.id.daily);

                if(isNetworkAvailable())
                {    loadIntoListView(reponseMessage);
                     spinner.setVisibility(View.GONE);
                     textView.setText(R.string.fashion);
                     textView2.setText(R.string.mobile);
                     textView3.setText(R.string.homeliving);
                     textView4.setText(R.string.daily);
                }
                else
                 {
                     textView2.setText("Kindly switch on Your data");
                     spinner.setVisibility(View.GONE);
                 }
               }
            catch (JSONException e)
            {
                e.printStackTrace();


            }

        }
    }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}