package com.example.somnath.kart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somnath.kart.adapters.Expandable_adapter;
import com.example.somnath.kart.product_category.Bags;
import com.example.somnath.kart.product_category.Children;
import com.example.somnath.kart.product_category.Jewellery;
import com.example.somnath.kart.product_category.Men;
import com.example.somnath.kart.product_category.Mobile;
import com.example.somnath.kart.product_category.Mobile_Accessories;
import com.example.somnath.kart.product_category.Sports;
import com.example.somnath.kart.product_category.TvAudioVideo;
import com.example.somnath.kart.product_category.Women;

import org.apache.http.MethodNotSupportedException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
   public static List<String> listDataHeader;
   public static HashMap<String, List<String>> listDataChild;
    int anInt=0,anInt2=0;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //drawerlayout started
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();




        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(new SessionManager(getApplicationContext()).checkLogin())
                {
                    HashMap<String, String> user = new SessionManager(getApplicationContext()).getUserDetails();
                    String nameof = user.get(SessionManager.KEY_NAME);
                    String emailof = user.get(SessionManager.KEY_EMAIL);
                    TextView name1=(TextView) findViewById(R.id.t2);
                    TextView  email1=(TextView) findViewById(R.id.t3);
                    name1.setText(nameof);
                    email1.setText(emailof);}
            }
             @Override
            public void onDrawerOpened(View drawerView) {

                 if(!new SessionManager(getApplicationContext()).isLoggedIn())
                 {
                    RelativeLayout relativeLayout=(RelativeLayout) drawerView.findViewById(R.id.navhhhh);

                     relativeLayout.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         Intent intent = new Intent(view.getContext(), SignUporLoginActivity.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                         startActivity(intent);
                         closeDrawer();
                     }
                 });
                 }
            }
             @Override
            public void onDrawerClosed(View drawerView) {
             }
             @Override
            public void onDrawerStateChanged(int newState) {}
        });



           //Fragments to replace

        if(getIntent().getIntExtra("fragmentNumber",0)==1) {
            if (findViewById(R.id.fragmentContainer) != null) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AfterLoginFragment(),"after").commit();
               anInt2++;
            }
        }
        else if(getIntent().getIntExtra("f",0)==2) {
            if (findViewById(R.id.fragmentContainer) != null) {

                Fragment firstFragment = new AccountFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, firstFragment,"acc").commit();
                anInt++;

            }
           }
            else{
                if (findViewById(R.id.fragmentContainer) != null) {
                    Fragment firstFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, firstFragment,"home").commit();
                }
            }
        //bottomnavigation view started

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                String s="";
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment=new HomeFragment();
                        s=s+"home";
                        break;
                    case R.id.categories:
                        fragment=new CategoryFragment();
                        s=s+"cat";
                        break;
                    case R.id.navigation_account:
                        if(new SessionManager(getApplicationContext()).checkLogin())
                        {  fragment=new AfterLoginFragment();
                            s=s+"after";}
                        else
                       { fragment=new AccountFragment();
                            s=s+"acc";}
                        break;
                }
                Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (!current.getClass().equals(fragment.getClass())) {

                    //POP BACKSTACK HERE LIKE EXPLAINED BELOW

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    final String tag = fragment.getClass().getSimpleName();
                    transaction.addToBackStack(tag);
                    transaction.replace(R.id.fragmentContainer, fragment, tag);
                    transaction.commit();
                    return true;
                } else {
                    //THROW AN ERROR OR SOMETHING, HANDLE IT BEING THE SAME
                    return false;
                }

            }
        };


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // get the listview
        final ExpandableListView  expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // preparing list data
        prepareListData();
        Expandable_adapter listAdapter = new Expandable_adapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int child, long l) {
             if(i==0)
             {  switch (child)
                  {   case 0:startFragment(new Men());
                      closeDrawer();
                      break;
                      case 1:startFragment(new Women());
                          closeDrawer();
                          break;
                      case 2:startFragment(new Children());
                          closeDrawer();
                          break;
                      case 3:startFragment(new Sports());
                          closeDrawer();
                          break;
                      case 4:startFragment(new Jewellery());
                          closeDrawer();
                          break;
                      case 5:startFragment(new Bags());
                          closeDrawer();
                          break;

                  }

             }else
             if(i==1)
             {  switch (child)
                {case 0:startFragment(new Mobile());
                    closeDrawer();
                    break;
                 case 1:startFragment(new Mobile_Accessories());
                     closeDrawer();
                     break;
                 case 2:startFragment(new TvAudioVideo());
                     closeDrawer();
                     break;


                }

             }

                return true;
            }
        });
       if(getIntent().getStringExtra("Extra")!=null){
        switch (getIntent().getStringExtra("Extra"))
        {
            case "men":
                startFragment(new Men());
                break;
            case "women":
                startFragment(new Women());
                break;
            case "children":
                startFragment(new Children());
                break;
            case "sports":
                startFragment(new Sports());
                break;
            case "jewellery":
                startFragment(new Jewellery());
                break;
            case "bags":
                startFragment(new Bags());
                break;
            case "phone":
                startFragment(new Mobile());
                break;
            case "accessories":
                startFragment(new Mobile_Accessories());
                break;
            case "tv":
                startFragment(new TvAudioVideo());
                break;

        }



    }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment =getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        int f=getSupportFragmentManager().getBackStackEntryCount();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        if(anInt==1)
        {   if(findViewById(R.id.fragmentContainer)!=null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();
            anInt++;
        }
        else
        if(anInt2==1)
        {  if(findViewById(R.id.fragmentContainer)!=null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();
            anInt2++;
        }
        else
        if((f==0)&&currentFragment instanceof HomeFragment)
         {new AlertDialog.Builder(this)
                     .setIcon(android.R.drawable.ic_dialog_alert)
                     .setTitle("Closing Activity")
                     .setMessage("Are you sure you want to close this activity?")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                     {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             finish();
                         }

                     })
                     .setNegativeButton("No", null)
                     .show();
         }else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public static void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Fashion");
        listDataHeader.add("Mobile & Electronics");
        listDataHeader.add("Home & Living");
        listDataHeader.add("Daily Needs");
        // Adding child data
        List<String> f = new ArrayList<String>();
        f.add("Men's Fashion");
        f.add("Women's Fashion");
        f.add("Children");
        f.add("Sports & Fitness");
        f.add("Jewellery");
        f.add("Bags & Luggages");

        List<String> mobile = new ArrayList<String>();
        mobile.add("Mobiles Phone");
        mobile.add("Mobiles Accessories");
        mobile.add("TV, Audio, Video");
        mobile.add("Personal Care Appliances");
        mobile.add("Cameras & Accessories");
        mobile.add("Appliances");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        List<String> comingSoon2 = new ArrayList<String>();
        comingSoon2.add("2 Guns");
        comingSoon2.add("The Smurfs 2");
        comingSoon2.add("The Spectacular Now");
        comingSoon2.add("The Canyons");
        comingSoon2.add("Europa Report");

        listDataChild.put(listDataHeader.get(0),f); // Header, Child data
        listDataChild.put(listDataHeader.get(1), mobile);
        listDataChild.put(listDataHeader.get(2), comingSoon);
        listDataChild.put(listDataHeader.get(3),comingSoon2);
    }

    private void startFragment(Fragment fragment)
    {
        FragmentManager currentFragment =getSupportFragmentManager();

        currentFragment.beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
    }
    private void closeDrawer()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

}
