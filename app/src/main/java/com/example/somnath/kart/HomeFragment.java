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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somnath.kart.adapters.CustomGrid;
import com.example.somnath.kart.adapters.DataAdapter;
import com.example.somnath.kart.adapters.SlidingImageAdapter;
import com.example.somnath.kart.utils.CheckNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {
    ViewPager viewPager,viewPager2,viewPager3,viewPager4;
    private ProgressBar progressBar;
    private WebRequestReceiver2 receiver;
    private  ProgressDialog progressDialog;
    private Context context;
    private GridView grid;
    private  TextView textView2,textView1;
    private RecyclerView recyclerView1,recyclerView2;
    private RecyclerView.Adapter mAdapter1,mAdapter2,mAdapter3,mAdapter4;
    private RecyclerView.LayoutManager mLayoutManager,mLayoutManager2,mLayoutManager3,mLayoutManager4;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_view, container, false);
        progressBar=(ProgressBar) v.findViewById(R.id.progresshome) ;
        progressBar.setVisibility(View.VISIBLE);



        return v;
    }



    @Override
    public void onResume() {

        super.onResume();

        IntentFilter filter = new IntentFilter(HomeFragment.WebRequestReceiver2.PROCESS_RESPONSE2);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new WebRequestReceiver2();
        getActivity().registerReceiver(receiver,filter);

        Intent msgIntent = new Intent(getActivity(), WebRequestService.class);
        msgIntent.putExtra(WebRequestService.REQUEST_STRING, "https://improvised-lots.000webhostapp.com/home.php");
        getActivity().startService(msgIntent);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("SomKart");

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    public class WebRequestReceiver2 extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE2 = "package com.example.somnath.kart.intent.action.PROCESS_RESPONSE2";

        @Override
        public void onReceive(Context context, Intent intent) {
            //creating a json array from the json string
            String response = intent.getStringExtra(WebRequestService.RESPONSE_MESSAGE);

            try {   viewPager = (ViewPager) getView().findViewById(R.id.pager);
                    textView2=(TextView) getView().findViewById(R.id.error);
                    textView1=(TextView) getView().findViewById(R.id.hotdeals);
                    recyclerView1=(RecyclerView) getView().findViewById(R.id.homerecycler1);
                    recyclerView2=(RecyclerView) getView().findViewById(R.id.homerecycler2);

                    if(isNetworkAvailable()) {

                        JSONObject jsonObject=new JSONObject(response);

                        JSONArray jsonArray=jsonObject.getJSONArray("slide");
                        JSONArray jsonArray2=jsonObject.getJSONArray("deals");


                        String [] slide = new String[jsonArray.length()];
                        String [] name=new String [jsonArray2.length()];
                        String []image=new String [jsonArray2.length()];
                        String[] newprice=new String[jsonArray2.length()];
                        String []oldprice=new String [jsonArray2.length()];
                        String[] discount=new String[jsonArray2.length()];


                        //looping through all the elements in json array
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //getting json object from the json array
                            JSONObject obj = jsonArray.getJSONObject(i);
                            //getting the name from the json object and putting it inside string array
                            slide[i] = obj.getString("trendingslide");
                        }
                        //looping through jsonArray2
                        for(int j=0;j<jsonArray2.length();j++)
                        {
                           JSONObject obj2=jsonArray2.getJSONObject(j);

                            image[j]=obj2.getString("pimage");
                            name[j]=obj2.getString("pname");
                            newprice[j]=obj2.getString("newprice");

                            oldprice[j]=obj2.getString("pprice");
                            discount[j]=obj2.getString("pdiscount");

                            newprice[j]=name[j]+"\n"+"Rs."+oldprice[j]+ "-"+discount[j]+"%"+"\n"+"Rs."+newprice[j];
                        }

                        SlidingImageAdapter slidingImageAdapter = new SlidingImageAdapter(getContext(), slide);
                        viewPager.setAdapter(slidingImageAdapter);


                        recyclerView1= (RecyclerView) getView().findViewById(R.id.homerecycler1);
                        recyclerView2 = (RecyclerView) getView().findViewById(R.id.homerecycler2);

                        recyclerView1.setNestedScrollingEnabled(false);
                        recyclerView2.setNestedScrollingEnabled(false);

                        mLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
                        mLayoutManager2 = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);

                        recyclerView1.setLayoutManager(mLayoutManager);
                        recyclerView2.setLayoutManager(mLayoutManager2);

                        mAdapter1 = new DataAdapter(getActivity(),newprice,image,"home");
                        mAdapter2 = new DataAdapter(getActivity(), newprice,image,"home");

                        recyclerView1.setAdapter(mAdapter1);
                        recyclerView2.setAdapter(mAdapter2);

                        textView1.setText(R.string.hot_deals);
                        progressBar.setVisibility(View.GONE);

                    }
                    else
                        {
                            textView2.setText("Kindly switch on Your data");
                            progressBar.setVisibility(View.GONE);

                        }


                } catch (JSONException e) {
                    e.printStackTrace();


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