package com.example.somnath.kart;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SOMNATH on 23-09-2017.
 */
public class AfterLoginFragment  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        View v= inflater.inflate(R.layout.afterlogin, container, false);


        SessionManager sessionManager=new SessionManager(v.getContext());
        ArrayAdapter<String> adpater,adpater2;

        List<String> account_list= new ArrayList<String>();
        List<String> list=new ArrayList<String>();

        HashMap<String, String> user = sessionManager.getUserDetails();
            String nameof = user.get(SessionManager.KEY_NAME);
            String emailof = user.get(SessionManager.KEY_EMAIL);
            TextView name1=(TextView) v.findViewById(R.id.name2);
            TextView  email1=(TextView) v.findViewById(R.id.emailll);
            TextView   text1=(TextView) v.findViewById(R.id.quickhw);
            name1.setText(nameof);
            email1.setText(emailof);

            text1.setText("Others");

            account_list.add("My Orders");
            account_list.add("My Cart");

            adpater=new ArrayAdapter<String> (v.getContext(),android.R.layout.simple_list_item_1,account_list);

            ListView listView=(ListView) v.findViewById(R.id.listvieww);
            listView.setAdapter(adpater);
            list.add("Go to website");
            list.add("Need Help");
            list.add("Feedback");
            list.add("Settings");
            list.add("About Me");
            list.add("Log Out");
            adpater2=new ArrayAdapter<String> (v.getContext(),android.R.layout.simple_list_item_1,list);
            ListView listView2=(ListView) v.findViewById(R.id.secondlistw);
            listView2.setAdapter(adpater2);
        setListViewHeightBasedOnChildren(listView);
        setListViewHeightBasedOnChildren(listView2);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {switch(position)
                {case 0:
                    break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        SessionManager sessionManager=new SessionManager(getActivity());
                        sessionManager.logoutUser();
                        // After logout redirect user to Loing Activity
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // Staring Login Activity
                        intent.putExtra("f",2);
                        startActivity(intent);
                        break;

                }
                
            }
        });


        return v;
     }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Account");
    }

}
