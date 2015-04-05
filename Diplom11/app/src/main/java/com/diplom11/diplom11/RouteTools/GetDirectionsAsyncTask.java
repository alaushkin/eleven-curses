package com.diplom11.diplom11.RouteTools;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.diplom11.diplom11.RouteActivity;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Map;

public class GetDirectionsAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList>
{
    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";
    private RouteActivity activity;
    private Exception exception;
    private ProgressDialog progressDialog;

    public GetDirectionsAsyncTask(RouteActivity activity)
    {
        super();
        this.activity = activity;
    }

    public void onPreExecute()
    {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Calculating directions");
        progressDialog.show();
    }

    @Override
    public void onPostExecute(ArrayList result)
    {
        progressDialog.dismiss();
        if (exception == null)
        {
            activity.handleGetDirectionsResult(result);
        }
        else
        {
            processException();
        }
    }

    @Override
    protected ArrayList doInBackground(Map<String, String>... params)
    {
        Map<String, String> paramMap = params[0];
        try
        {
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
            GMapV2Direction md = new GMapV2Direction();
            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
            ArrayList directionPoints = md.getDirection(doc);
            return directionPoints;
        }
        catch (Exception e)
        {
            exception = e;
            return null;
        }
    }

    private void processException()
    {
        //Toast.makeText(activity, activity.getString(R.string.error_when_retrieving_data), 3000).show();
    }
}