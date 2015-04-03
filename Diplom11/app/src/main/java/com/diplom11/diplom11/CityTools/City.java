package com.diplom11.diplom11.CityTools;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

/**
 * Created by Mak on 03.04.2015.
 */
public class City {
    private String name;
    private double latitude;
    private double longitude;
    private String id;

    public City(){
        name = "";
        id = "";
    }

    public City(ParseObject object){
        name = object.getString("name");
        latitude = object.getDouble("latitude");
        longitude = object.getDouble("longitude");
        id = object.getObjectId();
    }

    @Override
    public String toString(){
        return name;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LatLng getCoordinate() {
        return new LatLng(latitude, longitude);
    }
}
