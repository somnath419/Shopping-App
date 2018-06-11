package com.example.somnath.kart.product_category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.somnath.kart.R;
import com.example.somnath.kart.adapters.AppController;
import com.example.somnath.kart.adapters.DataAdapter;

public class Men extends Activity {

    private String urlJsonArry = "https://improvised-lots.000webhostapp.com/men.php";
    private static String TAG = Men.class.getSimpleName();
    private ProgressDialog pDialog;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager;

    private Context context;
    // temporary string to show the parsed response

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.men);
        context=this;



        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


    }

    @Override
    protected void onResume() {
        super.onResume();
        makeJsonArrayRequest();
    }

    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            String [] img = new String[response.length()];
                            String [] name=new String [response.length()];

                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject men = (JSONObject) response
                                        .get(i);

                                name[i]= men.getString("p_name");
                                img[i]= men.getString("p_img");

                            }

                            mRecyclerView = (RecyclerView) findViewById(R.id.menrecycle);
                            mLayoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);


                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mAdapter1 = new DataAdapter(context,name,img, "home");
                            mRecyclerView.setAdapter(mAdapter1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}