package com.diplom11.diplom11;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;

import com.diplom11.diplom11.DictionaryTools.Dictionary;
import com.diplom11.diplom11.RouteTools.GetDirectionsAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@EActivity(R.layout.activity_route)
@OptionsMenu(R.menu.menu_common)
public class RouteActivity extends FragmentActivity {
    private GoogleMap map;
    private LatLng rStart, rEnd;

    @FragmentById SupportMapFragment mapFragment;

    @Extra String startCity, endCity;

    @AfterViews void init(){
        map = mapFragment.getMap();
        if (map == null) {
            finish();
            return;
        }
        rStart = Dictionary.getCityById(startCity).getCoordinate();
        rEnd = Dictionary.getCityById(endCity).getCoordinate();

        map.addMarker(new MarkerOptions().position(rEnd).title(Dictionary.getCityById(endCity).getName()));
        map.addMarker(new MarkerOptions().position(rStart).title(Dictionary.getCityById(startCity).getName()));
        map.animateCamera(CameraUpdateFactory.newLatLng(rEnd));
        findDirections(rStart.latitude, rStart.longitude, rEnd.latitude, rEnd.longitude, "driving");
    }

    protected String constructURL() {
        String sJsonURL = "http://maps.googleapis.com/maps/api/directions/json?";

        final StringBuffer mBuf = new StringBuffer(sJsonURL);
        mBuf.append("origin=");
        mBuf.append(rStart.latitude);
        mBuf.append(',');
        mBuf.append(rStart.longitude);
        mBuf.append("&destination=");
        mBuf.append(rEnd.latitude);
        mBuf.append(',');
        mBuf.append(rEnd.longitude);
        mBuf.append("&sensor=true&mode=");
        mBuf.append("driving");

        return mBuf.toString();
    }

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints)
    {
        Polyline newPolyline;
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLUE);
        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        newPolyline = map.addPolyline(rectLine);
    }

    @OptionsItem(R.id.toMainMenu)
    void myMethod() {
        MainMenuActivity_.intent(this).start();
    }

}
