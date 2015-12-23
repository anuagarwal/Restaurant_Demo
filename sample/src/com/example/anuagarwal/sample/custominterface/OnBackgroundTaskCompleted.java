package com.example.anuagarwal.sample.custominterface;

import com.example.anuagarwal.sample.model.Restaurant;

import java.util.List;

/**
 * Created by Anu Agarwal on 12/22/2015.
 */
public interface OnBackgroundTaskCompleted {
    abstract void backgroundTaskCompleted(List<Restaurant> restaurants);
}
