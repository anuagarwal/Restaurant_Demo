package com.example.anuagarwal.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Anu Agarwal on 12/19/2015.
 */
public class RestaurantFragment extends Fragment{

    public static Fragment newInstance() {
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        return restaurantFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
