package com.example.anuagarwal.sample.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.anuagarwal.sample.model.Restaurant;
import com.example.anuagarwal.sample.custominterface.OnBackgroundTaskCompleted;
import com.example.anuagarwal.sample.util.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anu Agarwal on 12/19/2015.
 */
public class GetRestuarantList extends AsyncTask<Void,Void,List<Restaurant>> {

    Context mContext;
    private ProgressDialog mProgressDialog;
    OnBackgroundTaskCompleted onBackgroundTaskCompleted;

    public GetRestuarantList(Context context,OnBackgroundTaskCompleted onBackgroundTaskCompleted ){
        mContext = context;
        this.onBackgroundTaskCompleted = onBackgroundTaskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Fetching the data from server...");
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.show();
    }

    @Override
    protected List<Restaurant> doInBackground(Void... voids) {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try {

            JSONObject jsonObject = Json.getJson(mContext);
            if(jsonObject != null) {
                JSONArray array = jsonObject.getJSONArray("data");
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Restaurant restaurant = new Restaurant();
                        restaurant.setName(object.getString("OutletName"));
                        restaurant.setNumOfCoupons(Integer.parseInt(object.getString("NumCoupons")));
                        restaurant.setLatitude(Float.parseFloat(object.getString("Latitude")));
                        restaurant.setLongitude(Float.parseFloat(object.getString("Longitude")));
                        restaurant.setImageUrl(object.getString("LogoURL"));
                        restaurant.setNeighbourhoodName(object.getString("NeighbourhoodName"));
                        JSONArray jsonArray = object.getJSONArray("Categories");
                        List<String> categories = new ArrayList<>();
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject category = jsonArray.getJSONObject(j);
                            if (!category.getString("Name").equalsIgnoreCase("Restaurant")) {
                                categories.add(category.getString("Name"));
                            }
                        }
                        restaurant.setCategories(categories);
                        restaurants.add(restaurant);
                    }
                }
            }else{
                return null;
                //this.cancel(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {
        super.onPostExecute(restaurants);
        System.out.print(restaurants);
        onBackgroundTaskCompleted.backgroundTaskCompleted(restaurants);
        mProgressDialog.dismiss();
    }
}
