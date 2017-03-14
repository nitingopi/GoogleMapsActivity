package com.example.test.googlemapsactivity.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.googlemapsactivity.R;
import com.example.test.googlemapsactivity.utils.MapUtil;

public class Main2Activity extends AppCompatActivity implements NavigationLargeScreenFragment.ilargeFragment, NavigationFullScreenFragment.ifullScreenFragment{

    FragmentTransaction transaction;
    MapsFragment mapsFragment;
    NavigationDefaultFragment navDefFragment;
    NavigationFullScreenFragment navFullScrFragment;
    NavigationMinScreenFragment navMinScrFragment;
    NavigationLargeScreenFragment navlargeScrFragment;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 98;
    Button defBtn, minBtn, fullScrBtn, largeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        defBtn = (Button)findViewById(R.id.defaultBtn);
        minBtn = (Button)findViewById(R.id.minBtn);
        largeBtn = (Button)findViewById(R.id.largeBtn);
        fullScrBtn = (Button)findViewById(R.id.fullScreenBtn);
        MapUtil.getInstance().checkNavigationPermissions(this);
  /*      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //this will show
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        defBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeDefaultNaviMaps();
            }
        });
        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeSmallNaviMaps();
            }
        });
        largeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeLargeNaviMaps();
            }
        });
        fullScrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeFullScreenNaviMaps();
            }
        });

    }

    public void initializeSmallNaviMaps(){
        transaction = getSupportFragmentManager().beginTransaction();
        if(navMinScrFragment == null)
            navMinScrFragment = new NavigationMinScreenFragment();
        transaction.replace(R.id.container, navMinScrFragment, "B");
        transaction.commit();
    }
    public void initializeLargeNaviMaps(){
        transaction = getSupportFragmentManager().beginTransaction();
        if(navlargeScrFragment == null)
            navlargeScrFragment = new NavigationLargeScreenFragment();
        navlargeScrFragment.setCommunicator(Main2Activity.this);
        transaction.replace(R.id.container, navlargeScrFragment, "C");
        transaction.commit();
    }
    public void initializeFullScreenNaviMaps(){
        transaction = getSupportFragmentManager().beginTransaction();
        if(navFullScrFragment == null)
            navFullScrFragment = new NavigationFullScreenFragment();
        navFullScrFragment.setCommunicator(Main2Activity.this);
        transaction.replace(R.id.container, navFullScrFragment, "D");
        transaction.commit();
    }
    public void initializeDefaultNaviMaps(){
        transaction = getSupportFragmentManager().beginTransaction();
        if(navDefFragment == null)
            navDefFragment = new NavigationDefaultFragment();
        transaction.replace(R.id.container, navDefFragment, "A");
        transaction.commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                         MapUtil.getInstance().buildGoogleApiClient();

                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();

                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }


    @Override
    public void showFullScreen() {
        initializeFullScreenNaviMaps();
    }

    @Override
    public void showDefaultView() {
       initializeLargeNaviMaps();
    }
}
