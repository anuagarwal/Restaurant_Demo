package com.example.anuagarwal.sample.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anu Agarwal on 12/19/2015.
 */
public class Restaurant {

    String name;
    String imageUrl;
    int numOfCoupons;
    float latitude;
    float longitude;
    List<String> categories = new ArrayList<String>();
    String neighbourhoodName;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNumOfCoupons(int numOfCoupons) {
        this.numOfCoupons = numOfCoupons;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setNeighbourhoodName(String neighbourhoodName) {
        this.neighbourhoodName = neighbourhoodName;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public int getNumOfCoupons() {
        return numOfCoupons;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getNeighbourhoodName() {
        return neighbourhoodName;
    }
}
