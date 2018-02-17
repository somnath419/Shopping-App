package com.example.somnath.kart;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by SOMNATH on 06-09-2017.
 */

public class RegisterFragment extends Fragment{
    private EditText etFullName, etPassword, city, address,etEmail,contact;
    private ProgressBar progress;
    private SignUpActivity signUpActivity;
    private Context context;
    int i=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

       View v=inflater.inflate(R.layout.register_view,container,false);
        etFullName = (EditText) v.findViewById(R.id.etFullName);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etPassword = (EditText) v.findViewById(R.id.etPassword);
        contact=(EditText) v.findViewById(R.id.contact);
        city=(EditText)  v.findViewById(R.id.city);
        address=(EditText) v.findViewById(R.id.address);
        progress=(ProgressBar) v.findViewById(R.id.progressreg);
        progress.setVisibility(View.GONE);

        context=getContext();


        Button button=(Button) v.findViewById(R.id.btnSignup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String fullName = etFullName.getText().toString();
                String email=etEmail.getText().toString();
                String passWord = etPassword.getText().toString();
                String phoneNumber = contact.getText().toString();
                String cities = city.getText().toString();
                String addresss=address.getText().toString();

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(context, "Enter Your Full Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(context, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passWord)) {
                    Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(context, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cities)) {
                    Toast.makeText(context, "Enter your city Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(addresss)) {
                    Toast.makeText(context, "Enter your address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!new Check_Functions().isValidEmail(email)) {
                    etEmail.setError("Invalid Email");
                    return;
                }

                if (!new Check_Functions().isValidPassword(passWord)) {
                    etPassword.setError("Password length should be greater than 6");
                    return;
                }



                signUpActivity=new SignUpActivity(view.getContext());
                signUpActivity.execute(fullName,email, passWord, phoneNumber,cities,addresss);
                i++;
            }
        });
        return  v;
    }



    public class SignUpActivity extends AsyncTask<String, Void, String> {
        private Context context;
        public SignUpActivity(Context context) {
            this.context = context;
        }
        protected void onPreExecute() {
            progress=(ProgressBar) getActivity().findViewById(R.id.progressreg);
            progress.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... arg0) {
            String fullName = arg0[0];
            String email = arg0[1];
            String passWord = arg0[2];
            String phoneNumber = arg0[3];
            String cities = arg0[4];
            String addresss=arg0[5];
            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                link = "https://improvised-lots.000webhostapp.com/signup.php";
                data= URLEncoder.encode("name", "UTF-8") + "=" +
                        URLEncoder.encode(fullName, "UTF-8");
                data+= "&"+ URLEncoder.encode("e-mail", "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");
                data+= "&"+ URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(passWord, "UTF-8");
                data+= "&"+ URLEncoder.encode("contact", "UTF-8") + "=" +
                        URLEncoder.encode(phoneNumber, "UTF-8");
                data+= "&"+ URLEncoder.encode("city", "UTF-8") + "=" +
                        URLEncoder.encode(cities, "UTF-8");
                data+= "&"+ URLEncoder.encode("address", "UTF-8") + "=" +
                        URLEncoder.encode(addresss, "UTF-8");
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

                    if (query_result.equals("Already")) {
                        Toast.makeText(context,"You are already a user Redirecting to Login Page", Toast.LENGTH_SHORT).show();

                        if (getActivity().findViewById(R.id.Frame) != null) {

                            RelativeLayout relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.rel);
                            relativeLayout.setVisibility(View.GONE);
                            LoginFragment firstFragment = new LoginFragment();
                            firstFragment.setArguments(getActivity().getIntent().getExtras());
                           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Frame, firstFragment).addToBackStack(null).commit();
                           progress.setVisibility(View.GONE);
                        }
                    }
                    else
                        if(query_result.equals("Gotoaccount")) {

                            String emaill=jsonObj.getString("email");
                            String name=jsonObj.getString("name");
                        SessionManager sessionManager=new SessionManager(getActivity());
                        sessionManager.createLoginSession(name,emaill);

                        Intent intent =new Intent(getActivity().getApplicationContext(),MainActivity.class);
                        intent.putExtra("fragmentNumber",1);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                            Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show();
                         progress.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Network Connection Problem", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause(){
        if(i!=0)
        {signUpActivity.cancel(true);
        }
        super.onPause();


    }





}





