package com.diplom11.diplom11;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_route)
public class Route extends FragmentActivity {
    @FragmentById SupportMapFragment mapFragment;
    GoogleMap map;

    @AfterViews void init() {
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        map = mapFragment.getMap();
        if (map == null) {
            finish();
            return;
        }
    }
}
