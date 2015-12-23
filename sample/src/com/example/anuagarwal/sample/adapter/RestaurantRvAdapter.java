package com.example.anuagarwal.sample.adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.viewpager.extensions.sample.R;
import com.example.anuagarwal.sample.lazyloading.ImageLoader;
import com.example.anuagarwal.sample.model.Restaurant;

import java.util.List;

/**
 * Created by Anu Agarwal on 12/15/2015.
 */
public class RestaurantRvAdapter extends RecyclerView.Adapter<RestaurantRvAdapter.FormViewHolder> {
    private final ImageLoader imageLoader;
    List<Restaurant> restaurantList;
    private Location currentLocation;

    public RestaurantRvAdapter(List<Restaurant> restaurantList,Location currentLocation,Context context){
        this.restaurantList = restaurantList;
        this.currentLocation = currentLocation;
        imageLoader=new ImageLoader(context);
    }

    @Override
    public FormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_restaurant_list_item, parent, false);
        FormViewHolder pvh = new FormViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FormViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.restaurantNameTv.setText(restaurant.getName());
        if(restaurant.getNumOfCoupons() == 1){
            holder.numberCouponsTv.setText(String.valueOf(restaurant.getNumOfCoupons()) + " Offer");
        }else {
            holder.numberCouponsTv.setText(String.valueOf(restaurant.getNumOfCoupons()) + " Offers");
        }
        List<String> categories = restaurant.getCategories();
        StringBuilder stringBuilder = new StringBuilder();
        for (String category : categories){
            stringBuilder.append("&#8226;" + category+ " ");
        }
        holder.categoriesTv.setText(Html.fromHtml(stringBuilder.toString()));
        Location location = new Location("destination");
        location.setLatitude(restaurant.getLatitude());
        location.setLongitude(restaurant.getLongitude());
        if(currentLocation != null) {
            float distance = currentLocation.distanceTo(location);
            holder.distanceTv.setText(String.valueOf(distance) + " " + restaurant.getNeighbourhoodName());
        }
        imageLoader.DisplayImage(restaurant.getImageUrl(), holder.restaurantLogoIv);
}

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public static class FormViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView restaurantNameTv;
        TextView numberCouponsTv;
        TextView categoriesTv;
        TextView distanceTv;
        ImageView restaurantLogoIv;

        FormViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            restaurantNameTv = (TextView) itemView.findViewById(R.id.restaurantNameTv);
            numberCouponsTv = (TextView) itemView.findViewById(R.id.numberCouponsTv);
            categoriesTv = (TextView) itemView.findViewById(R.id.categoriesTv);
            distanceTv = (TextView)itemView.findViewById(R.id.distanceTv);
            restaurantLogoIv = (ImageView) itemView.findViewById(R.id.restaurantLogoIv);
        }
    }
}
