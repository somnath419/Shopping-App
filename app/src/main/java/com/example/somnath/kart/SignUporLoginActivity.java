package com.example.somnath.kart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somnath.kart.adapters.Expandable_adapter;
import com.example.somnath.kart.utils.Check_Functions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by SOMNATH on 08-09-2017.
 */

public class SignUporLoginActivity extends AppCompatActivity{
    private EditText email;
    private ProgressBar progress;
    private Check check;
     int i=0;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);
        progress=(ProgressBar) findViewById(R.id.progressSign);
        progress.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBar actionBar=getSupportActionBar();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}
            @Override
            public void onDrawerOpened(View drawerView) {
                if(new SessionManager(getApplicationContext()).checkLogin())
                {
                    HashMap<String, String> user = new SessionManager(getApplicationContext()).getUserDetails();
                    String nameof = user.get(SessionManager.KEY_NAME);
                    String emailof = user.get(SessionManager.KEY_EMAIL);
                    TextView name1=(TextView) findViewById(R.id.t2);
                    TextView  email1=(TextView) findViewById(R.id.t3);
                    name1.setText(nameof);
                    email1.setText(emailof);}
                if(!new SessionManager(getApplicationContext()).isLoggedIn())
                {                 RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.navh23);
                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    });
                }
            }
            @Override
            public void onDrawerClosed(View drawerView) {}
            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        email = (EditText) findViewById(R.id.editText3);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailcheck = email.getText().toString();

                if (TextUtils.isEmpty(emailcheck)) {
                    Toast.makeText(SignUporLoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!new Check_Functions().isValidEmail(emailcheck)) {
                    email.setError("Invalid Email");
                    return;
                }

                check=new Check(view.getContext());
                check.execute(emailcheck);
                i++;

            }
        });

        // get the listview
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lvExp2);
        // preparing list data
        MainActivity.prepareListData();
        Expandable_adapter listAdapter = new Expandable_adapter(this,MainActivity.listDataHeader,MainActivity.listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int child, long l) {
                Intent intent=new Intent(SignUporLoginActivity.this,MainActivity.class);
                if(i==0)
                {

                    switch (child)
                {   case 0:
                    intent.putExtra("Extra","men");
                    startActivity(intent);
                    break;
                    case 1:
                        intent.putExtra("Extra","women");
                        startActivity(intent);
                        break;
                    case 2: intent.putExtra("Extra","children");
                        startActivity(intent);
                        break;
                    case 3: intent.putExtra("Extra","sports");
                        startActivity(intent);
                        break;
                    case 4:intent.putExtra("Extra","jewellery");
                        startActivity(intent);
                        break;
                    case 5: intent.putExtra("Extra","bags");
                        startActivity(intent);
                        break;

                }

                }else
                if(i==1)
                {  switch (child)
                {case 0: intent.putExtra("Extra","phone");
                    startActivity(intent);
                    break;
                    case 1:intent.putExtra("Extra","accessories");
                        startActivity(intent);
                        break;
                    case 2:intent.putExtra("Extra","tv");
                        startActivity(intent);
                        break;


                }
                }

                return true;
            }
        });
    }

    public class Check extends AsyncTask<String, Void, String> {
        private Context context;
        public Check(Context context) {
            this.context = context;
        }
        protected void onPreExecute() {

            progress=(ProgressBar) findViewById(R.id.progressSign);
            progress.setVisibility(View.VISIBLE);

        }


        @Override
        protected String doInBackground(String... arg0) {
            String emailcheck = arg0[0];
            String link;
            String data;
            String result;
            try {
                link = "https://improvised-lots.000webhostapp.com/checkfor.php";
                data = URLEncoder.encode("e-mail", "UTF-8") + "=" +
                        URLEncoder.encode(emailcheck, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    if(isCancelled())
                        return  null;
                    sb.append(line);
                    break;}
                result = sb.toString();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
        @Override
        protected void onCancelled() {
            Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String jsonStr = result;

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    if (query_result.equals("Login"))
                    {
                        EditText editText = (EditText) findViewById(R.id.editText3);
                        String email1 = editText.getText().toString();
                        if (findViewById(R.id.Frame) != null) {

                            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel);
                            relativeLayout.setVisibility(View.GONE);

                            LoginFragment firstFragment = new LoginFragment();
                            firstFragment.setArguments(getIntent().getExtras());
                            getSupportFragmentManager().beginTransaction().add(R.id.Frame, firstFragment).addToBackStack(null).commit();
                            progress.setVisibility(View.GONE);                        }
                    }
                    else
                        if (query_result.equals("SignUp"))
                        {

                        Toast.makeText(context, "Redirecting to Sign Up Page", Toast.LENGTH_SHORT).show();
                        if (findViewById(R.id.Frame) != null) {
                            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel);
                            relativeLayout.setVisibility(View.GONE);

                            RegisterFragment firstFragment = new RegisterFragment();
                            firstFragment.setArguments(getIntent().getExtras());
                            getSupportFragmentManager().beginTransaction().add(R.id.Frame, firstFragment).addToBackStack(null).commit();
                        progress.setVisibility(View.GONE);
                        }
                    }
                    else
                        {
                        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Network Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments ==0)
        {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public  void onDestroy()
    {   if(i!=0)
        {check.cancel(true);}
        super.onDestroy();
    }


}

