package com.example.test.googlemapsactivity.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.googlemapsactivity.R;
import com.example.test.googlemapsactivity.utils.MapUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by test on 6/3/17.
 */
public class NavigationDefaultFragment extends Fragment  {
    private View view;
    TextView route, currentLocation, module;
    MapUtil mapUtil;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapUtil = MapUtil.getInstance();
        view = inflater.inflate(R.layout.map_default_layout, container, false);
      //  route = (TextView) view.findViewById(R.id.route_default);
        module = (TextView) view.findViewById(R.id.module_name_default);
        currentLocation = (TextView) view.findViewById(R.id.current_location_default);

        System.out.println("NavigationDefaultFragment.onCreateView");

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(mapUtil.getCurPosition().latitude, mapUtil.getCurPosition().longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String streetName =  addresses.get(0).getAddressLine(0) + " "+addresses.get(0).getAddressLine(1);
        /*String cityName = addresses.get(0).getLocality();
        String stateName = addresses.get(0).getAddressLine(2);
        String countryName = addresses.get(0).getCountryName();*/
        currentLocation.setText(streetName);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();


    }
}
