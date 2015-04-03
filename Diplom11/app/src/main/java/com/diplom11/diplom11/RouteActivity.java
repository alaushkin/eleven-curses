package com.diplom11.diplom11;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.AfterViews;

@EActivity(R.layout.activity_route)
public class RouteActivity extends FragmentActivity {
    private GoogleMap map;
    private MarkerOptions markerEnd;
    private MarkerOptions markerStart;

    @FragmentById SupportMapFragment mapFragment;

    @Extra LatLng rStart, rEnd;

    @AfterViews void init(){
//        rStart = new LatLng(0,10);
        map = mapFragment.getMap();
        if (map == null) {
            finish();
            return;
        }
//        markerEnd = new MarkerOptions();
//        markerEnd.position(rEnd);
//        map.addMarker(markerEnd);
////map.
//        markerStart = new MarkerOptions();
//        markerStart.position(rStart);
//        map.addMarker(markerStart);

        //map.animateCamera(CameraUpdateFactory.newLatLng(rEnd));
    }
}
