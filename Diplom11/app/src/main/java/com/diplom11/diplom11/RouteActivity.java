package com.diplom11.diplom11;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.AfterViews;

@EActivity(R.layout.activity_route)
public class RouteActivity extends FragmentActivity {
    private GoogleMap map;
    private LatLng start;
    private LatLng end;

    @FragmentById SupportMapFragment mapFragment;

    @AfterViews void init(){
        start = new LatLng(0,10);
        map = mapFragment.getMap();
        if (map == null) {
            finish();
            return;
        }
    }
}
