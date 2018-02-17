package com.example.somnath.kart;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somnath.kart.utils.Check_Functions;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by SOMNATH on 07-09-2017.
 */

public class LoginFragment extends Fragment {
    private EditText email, password;
    private TextView article;
    private ProgressBar spinner;
    private Context context;


    private Check2 check2;
    int i=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_view, container, false);
        email = (EditText) v.findViewById(R.id.editText);
        password = (EditText) v.findViewById(R.id.editText2);
        Button button = (Button) v.findViewById(R.id.button5);
        spinner=(ProgressBar)v.findViewById(R.id.spin);
        context=getContext();

       spinner.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailcheck = email.getText().toString();
                String passwordcheck = password.getText().toString();

                if (TextUtils.isEmpty(passwordcheck)) {
                    Toast.makeText(context, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(emailcheck)) {
                    Toast.makeText(context, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!new Check_Functions().isValidEmail(emailcheck)) {
                    email.setError("Invalid Email");
                    return;
                }

                if (!new Check_Functions().isValidPassword(passwordcheck)) {
                    password.setError("Password length should be greater than 6");
                    return;
                }



                check2=new Check2(view.getContext());
                check2.execute(emailcheck, passwordcheck);
                i++;
            }
        });
        return  v;
    }

     class Check2 extends AsyncTask<String, Void, String> {
        private Context context;
        public Check2(Context context) {
            this.context = context;

        }
        protected void onPreExecute() {
            spinner=(ProgressBar) getActivity().findViewById(R.id.spin);
             spinner.setVisibility(View.VISIBLE);
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... arg0) {
            String emailcheck = arg0[0];
            String passwordcheck = arg0[1];
            String link;
            String data;
            String result;
            try {
                link = "https://improvised-lots.000webhostapp.com/login.php";
                data = URLEncoder.encode("e-mail", "UTF-8") + "=" +
                        URLEncoder.encode(emailcheck, "UTF-8");
                data+= "&"+ URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(passwordcheck, "UTF-8");
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
                    break;
                }
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
                    if(query_result.equals("Success")) {
                        String emaill=jsonObj.getString("email");
                        String name=jsonObj.getString("name");
                        Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show();
                        SessionManager sessionManager=new SessionManager(getActivity());
                        sessionManager.createLoginSession(name,emaill);
                        Intent intent =new Intent(getActivity().getApplicationContext(),MainActivity.class);
                        intent.putExtra("fragmentNumber",1);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        spinner.setVisibility(View.GONE);
                    }
                    else
                    if (query_result.equals("SignUp")) {
                        Toast.makeText(context, "Not here", Toast.LENGTH_SHORT).show();
                        if(getActivity().findViewById(R.id.Frame)!=null){RegisterFragment firstFragment = new RegisterFragment();
                        firstFragment.setArguments(getActivity().getIntent().getExtras());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Frame, firstFragment).addToBackStack(null).commit();
                            spinner.setVisibility(View.GONE);                        }

                    }
                    else
                    if(query_result.equals("Error")) {
                        Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                    }
                    catch (JSONException e)
                    {
                    e.printStackTrace();
                    Toast.makeText(context, "Network Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onPause(){
        if(i!=0){
            check2.cancel(true);}
        super.onPause();
    }


}
