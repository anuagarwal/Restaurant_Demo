/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.anuagarwal.sample.ui.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.viewpager.extensions.sample.R;
import com.example.anuagarwal.sample.adapter.RestaurantRvAdapter;
import com.example.anuagarwal.sample.asynctask.GetRestuarantList;
import com.example.anuagarwal.sample.model.Restaurant;
import com.example.anuagarwal.sample.service.GPSTracker;
import com.example.anuagarwal.sample.custominterface.OnBackgroundTaskCompleted;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NearByRestaurantFragment extends Fragment implements OnBackgroundTaskCompleted {

    private static final String ARG_POSITION = "position";
    List<Restaurant> restaurants = new ArrayList<>();
    private int position;
    private LocationListener mLocationListener;
    private RecyclerView restaurantRv;
    private RestaurantRvAdapter restaurantRvAdapter;
    private String provider;
    private Location currentLocation;
    TextView currentLocationTv;

    public static NearByRestaurantFragment newInstance() {
        NearByRestaurantFragment f = new NearByRestaurantFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        restaurantRv = (RecyclerView) rootView.findViewById(R.id.restaurantRv);
        restaurantRv.setHasFixedSize(true);
        currentLocationTv = (TextView) rootView.findViewById(R.id.currentLocationTv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        restaurantRv.setLayoutManager(llm);
        restaurantRvAdapter = new RestaurantRvAdapter(restaurants, currentLocation,getActivity());
        restaurantRv.setAdapter(restaurantRvAdapter);

        new GetRestuarantList(getActivity(), this).execute();
    }

    @Override
    public void backgroundTaskCompleted(List<Restaurant> restaurants) {
        // create class object
        GPSTracker gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {
            currentLocation = gps.getLocation();
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0);
                currentLocationTv.setText(address);
            }
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        if (restaurants == null || restaurants.size() == 0) {
            Toast.makeText(getActivity(), "No network available", Toast.LENGTH_LONG).show();
        } else {
            restaurantRvAdapter = new RestaurantRvAdapter(restaurants, currentLocation,getActivity());
            restaurantRv.setAdapter(restaurantRvAdapter);
        }
    }
}