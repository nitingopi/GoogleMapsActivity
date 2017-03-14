package com.example.test.googlemapsactivity.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.googlemapsactivity.R;
import com.example.test.googlemapsactivity.model.Example;
import com.example.test.googlemapsactivity.model.Step;
import com.example.test.googlemapsactivity.rest.ApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
/*import com.squareup.okhttp.OkHttpClient;*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;*/


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, MapsDrawerFragment.FragmentDrawerListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 98;
    public static final int RESULT_OK = -1;
    public static final int RESULT_CANCELED = 0;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    /*    private Location mLastLocation;*/
    private Marker mCurrLocationMarker;
    private SupportMapFragment mapFragment;
    /*    private MapsDrawerFragment drawerFragment;
        private Toolbar toolbar;*/
    private View view;
    FrameLayout mapContainer;
    /*   private PlaceAutocompleteFragment autocompleteFragment;*/
    private FloatingActionButton fab, fab1, fab2, fab3;
    private boolean FAB_Status = false;
    private LatLng origin, destination;
    Polyline line;
    MarkerOptions markerOptions;
    //Animations
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;

    private final static String API_KEY = "AIzaSyB8Im4qQb8p858srgTYuGRoyZJHJgKeG1c";
    private final static String MODE = "driving";
    List<Step> steps = null;

    TextView route, currentLocation, module;


    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //commented 0303
        //view = inflater.inflate(R.layout.fragment_map, container, false);
        view = inflater.inflate(R.layout.map_default_layout, container, false);
       /* route = (TextView) view.findViewById(R.id.route);
        module = (TextView) view.findViewById(R.id.module_name);
        currentLocation = (TextView) view.findViewById(R.id.current_location);*/

        System.out.println("MapsFragment.onCreateView");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }


      /*  SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        System.out.println("mapFragment = " + mapFragment);
        System.out.println("this = " + this);
        mapFragment.getMapAsync(this);*/
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("MapsFragment.onActivityCreated");
        //toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        FragmentManager fm = getChildFragmentManager();
        //commented 0303
        /*mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        mapContainer = (FrameLayout)view.findViewById(R.id.map_container);
        if (mMap == null) {

            mapFragment = SupportMapFragment.newInstance();
        //commented 0303
        //    fm.beginTransaction().replace(R.id.map_container, mapFragment).commit();

        }*/
/*        drawerFragment = (MapsDrawerFragment)fm.findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.layout.fragment_navigation_drawer, (DrawerLayout) view.findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);*/

     /*   autocompleteFragment = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                System.out.println("place = " + place);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                System.out.println("status = " + status);
            }
        });
*/
        //Floating Action Buttons
        //commented on 0306
    /*    fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) view.findViewById(R.id.fab_3);
*/
        //Animations
        //commented on 0603
      /*  show_fab_1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab3_hide);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    mapContainer.setAlpha(0.4f);
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    mapContainer.setAlpha(1f);
                    FAB_Status = false;
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Floating Action Button 1", Toast.LENGTH_SHORT).show();
                callPlaceAutocompleteActivityIntent();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "Floating Action Button 2", Toast.LENGTH_SHORT).show();
                showDirections();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Floating Action Button 3", Toast.LENGTH_SHORT).show();
            }
        });
*/
        /*fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        });*/


/***at this time google play services are not initialize so get map and add what ever you want to it in onResume() or onStart() **/
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("MapsFragment.onResume");
//commented on 0303
/*            if (mMap == null) {
                mMap = mapFragment.getMap();


                mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
            }*/
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("MapsFragment.onMapReady");
        //commented 0303
/*        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);*/

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                //commented 0303
/*                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);*/
            }
        } else {
            buildGoogleApiClient();
            //commented 0303
/*            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);*/
        }
    }

    public void buildGoogleApiClient() {
        System.out.println("MapsFragment.buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("MapsFragment.onConnected");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("MapsFragment.onConnectionSuspended");

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("MapsFragment.onLocationChanged");
       /* mLastLocation = location;*/
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        origin = new LatLng(location.getLatitude(), location.getLongitude());
        //commented 0303
       /* markerOptions = new MarkerOptions();
        markerOptions.position(origin);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));*/

        Toast.makeText(getActivity(), String.valueOf(origin.latitude) + " " + String.valueOf(origin.latitude), Toast.LENGTH_SHORT).show();


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        currentLocation.setText("Latitude : " + origin.latitude + " Longitude : " + origin.longitude);
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("MapsFragment.onConnectionFailed");

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
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
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        //commented 0303
                       /* mMap.setMyLocationEnabled(true);*/
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }


    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);
    }

    private void callPlaceAutocompleteActivityIntent() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//PLACE_AUTOCOMPLETE_REQUEST_CODE is integer for request code

        } catch (GooglePlayServicesRepairableException |

                GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode = " + requestCode);
        System.out.println("resultCode = " + resultCode);
        System.out.println("data = " + data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                System.out.println("place.getLatLng() = " + place.getLatLng());
                System.out.println("place.getLocale() = " + place.getLocale());
                System.out.println("place.getWebsiteUri() = " + place.getWebsiteUri());
                System.out.println("place.getAttributions() = " + place.getAttributions());
                System.out.println("place.getAddress() = " + place.getAddress());
                System.out.println("place.getPhoneNumber() = " + place.getPhoneNumber());
                System.out.println("place.getPlaceTypes() = " + place.getPlaceTypes());
                destination = place.getLatLng();
                markerOptions = new MarkerOptions();
                markerOptions.position(destination);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                mCurrLocationMarker = mMap.addMarker(markerOptions);
                build_retrofit_and_get_response(MODE);
                Toast.makeText(getActivity(), place.getAddress().toString(), Toast.LENGTH_SHORT).show();
                //txtlocation.setText(place.getAddress().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
            } else if (requestCode == RESULT_CANCELED) {

            }
        }
    }

    private void build_retrofit_and_get_response(String type) {
        System.out.println("MapsFragment.build_retrofit_and_get_response");
        String url = "https://maps.googleapis.com/maps/";
/*        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();*/
// add your other interceptors …
// add logging as last interceptor

        RetrofitMaps apiService = ApiClient.getClient().create(RetrofitMaps.class);
        //httpClient.addInterceptor(logging);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        // RetrofitMaps service = retrofit.create(RetrofitMaps.class);
        /*System.out.println( origin.latitude + "," + origin.longitude+" "+destination.latitude + "," + destination.longitude+" "+ type);*/
        Call<Example> call = apiService.getDistanceDuration(origin.latitude + "," + origin.longitude, destination.latitude + "," + destination.longitude, API_KEY);
        /*Call<Example> call = apiService.getDistanceDuration("metric", origin.latitude + "," + origin.longitude,destination.latitude + "," + destination.longitude, type,API_KEY);*/

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                /*System.out.println("MapsFragment.onResponse");
                System.out.println("response message = " + response.message());
                System.out.println("response.body() = " + response.body());
                System.out.println("response.isSuccess() = " + response.isSuccessful());
                System.out.println("response.body().getRoutes() = " + response.body().getRoutes());
                System.out.println("response.code = " + response.code());
                System.out.println("response.body().getRoutes().size() = " + response.body().getRoutes().size());*/

                try {
                    //Remove previous line from map
                    if (line != null) {
                        line.remove();
                    }
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        steps = response.body().getRoutes().get(i).getLegs().get(i).getSteps();


                        //Toast.makeText(getActivity(), "Distance:" + distance + ", Duration:" + time, Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Distance:" + distance + ", Duration:" + time, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //ShowDistanceDuration.setText("Distance:" + distance + ", Duration:" + time);

                        //modification starts
                        String encodedString = "";
                        List<LatLng> list = new ArrayList<>();
                        for (int j = 0; j < steps.size(); j++) {
                            //System.out.println("steps.size() j = " + steps.size()+" "+j);
                            /*System.out.println("response.body().getRoutes().get(j).getLegs().get(j).getSteps().get(j) = " + response.body().getRoutes().get(j).getLegs().get(j).getSteps().get(j));
                            System.out.println("response.body().getRoutes().get(j).getLegs().get(j).getSteps().get(j).getPolyline() = " + response.body().getRoutes().get(j).getLegs().get(j).getSteps().get(j).getPolyline());
                            System.out.println("response.body().getRoutes().get(j).getLegs().get(j).getSteps().get(j).getPolyline().getPoints() = " + response.body().getRoutes().get(j).getLegs().get(j).getSteps().get(j).getPolyline().getPoints());*/
                            encodedString = response.body().getRoutes().get(i).getLegs().get(i).getSteps().get(j).getPolyline().getPoints();
                            list.addAll(decodePoly(encodedString));

                        }

                        line = mMap.addPolyline(new PolylineOptions()
                                        .addAll(list)
                                        .width(15)
                                        .color(Color.MAGENTA)
                                        .geodesic(true)
                        );
                        //modification ends

                        //original code work starts
                        /*String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = decodePoly(encodedString);
                        line = mMap.addPolyline(new PolylineOptions()
                                        .addAll(list)
                                        .width(15)
                                        .color(Color.MAGENTA)
                                        .geodesic(true)
                        );*/
                        //original code work ends
                    }

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

       /*     @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                System.out.println("MapsFragment.onResponse");
                System.out.println("response message = " + response.message());
                System.out.println("response.body() = " + response.body());
                System.out.println("response.isSuccess() = " + response.isSuccessful());
                System.out.println("response.body().getRoutes() = " + response.body().getRoutes());
                System.out.println("response.code = " + response.code());
               
                System.out.println("retrofit = " + retrofit);
                try {
                    //Remove previous line from map
                    if (line != null) {
                        line.remove();
                    }
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();

                        Toast.makeText(getActivity(), "Distance:" + distance + ", Duration:" + time, Toast.LENGTH_SHORT).show();
                        //ShowDistanceDuration.setText("Distance:" + distance + ", Duration:" + time);
                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = decodePoly(encodedString);
                        line = mMap.addPolyline(new PolylineOptions()
                                        .addAll(list)
                                        .width(20)
                                        .color(Color.RED)
                                        .geodesic(true)
                        );
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }*/
        });

    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public void showDirections() {
        if (steps != null && steps.size() > 0) {
/*
            for (Step stp : steps
                    ) {
                System.out.println("stp.getPath() = " + stp.getPath());
                System.out.println("stp.getDistance() = " + stp.getDistance());
                System.out.println("stp.getDuration() = " + stp.getDuration());

            }
*/

            Intent intent = new Intent(getActivity(), MapDirectionsActivity.class);
            intent.putExtra("Steps", (Serializable) steps);
            startActivity(intent);
        }

    }


}
