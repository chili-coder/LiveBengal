package com.sohaghlab.livebengal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sohaghlab.livebengal.adapter.FragmentAdapter;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("লাইভ বেঙ্গল");
        navigationView = findViewById(R.id.navigationView);
        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager222);

        tabLayout.setupWithViewPager(viewPager);

        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragmentAdapter.addFragment(new VideoFragment(),"লাইভ টিভি");
        fragmentAdapter.addFragment(new RedioFragment(),"লাইভ রেডিও");
        viewPager.setAdapter(fragmentAdapter);

        //banner ads
        mAdView=findViewById(R.id.adViewMain);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        /////end baneer


        ///no internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.no_internet);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;

            Button retry = dialog.findViewById(R.id.retry);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();

        } else {

        } //end retry

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.home_nav:


                        break;
//                    case R.id.abroad_nav:
//                        Intent intent = new Intent(getApplication(), AbroadActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.share_nav:
//                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                        sharingIntent.setType("text/plain");
//                        String shareBody = " দেশী ভ্রমণ (Deshi Tour) \n Application Download Link: ";
//                        String downloadText="ApplicationDownload Link";
//                        String shareUrl= "https://play.google.com/store/apps/details?id=";
//                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareBody);
//                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//
//                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareUrl + getPackageName());
//                        startActivity(Intent.createChooser(sharingIntent, "Sharevia"));
//
//                        break;
//
//                    case R.id.rate_nav:
//                        try {
//                            Uri uri = Uri.parse("market://details?id=" +getPackageName());
//                            Intent in = new Intent(Intent.ACTION_VIEW,uri);
//                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(in);
//                        } catch (ActivityNotFoundException e){
//                            Uri uri =Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName());
//                            Intent in =new Intent(Intent.ACTION_VIEW,uri);
//                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(in);
//                        }
//                        break;
//
//                    case R.id.privacy_nav:
//                        Intent chromeIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://sites.google.com/view/deshitour/home"));
//                        startActivity(chromeIntent);
//                        break;
//
//                    case R.id.contact_nav:
//                        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
//                        String emailId = "sohaghlab@gmail.com";
//                        Uri data = Uri.parse("mailto:?subject=" + "Subject Here"+ "&body=" + "Your Massage Here" + "&to=" + emailId);
//                        mailIntent.setData(data);
//                        startActivity(Intent.createChooser(mailIntent, "Send mail..."));
//                        break;


                }
                return true;
            }
        });




    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "দেশী ভ্রমণ (Deshi Tour) \n Application Download Link: ";
                String downloadText="ApplicationDownload Link";
                String shareUrl= "https://play.google.com/store/apps/details?id=";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareBody);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareUrl + getPackageName());
                startActivity(Intent.createChooser(sharingIntent, "Sharevia"));

                break;

            case android.R.id.home:
                finish();
                return true;



            default:
                return super.onOptionsItemSelected(item);


        }
        return true;


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }







    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.exit_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                        finishAffinity();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }



}