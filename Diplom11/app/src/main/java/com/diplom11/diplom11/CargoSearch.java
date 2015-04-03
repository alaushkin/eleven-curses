package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.diplom11.diplom11.CargoSearchTools.Cargo;
import com.diplom11.diplom11.DictionaryTools.Dictionary;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_cargo_search)
public class CargoSearch extends ActionBarActivity {
    @ViewById
    ListView listViewCargo;
    @ViewById
    Button cargoOrderByAddDate, cargoOrderByCost, cargoOrderByArriveDate;

    @Extra
    String date, fromCity, toCity, bodyType, loadType, payType;
    @Extra
    Double fromWeight, toWeight, fromVolume, toVolume;

    private ArrayList<Cargo> cargos = new ArrayList<>();
    private int orderMode;
    private Intent intent;
    public PopupMenu popupMenu;


    @Click
    void cargoOrderByAddDate() {
        if (orderMode == 0) {
            orderMode = 1;
        } else {
            orderMode = 0;
        }
        loadCargos();
    }

    @Click
    void cargoOrderByCost() {
        if (orderMode == 3) {
            orderMode = 2;
        } else {
            orderMode = 3;
        }
        loadCargos();
    }

    @Click
    void cargoOrderByArriveDate() {
        if (orderMode == 5) {
            orderMode = 4;
        } else {
            orderMode = 5;
        }
        loadCargos();
    }

    @ItemClick
    void listViewCargo(final Cargo cargo) {
        popupMenu = new PopupMenu(this, listViewCargo);
        popupMenu.inflate(R.menu.cspopupmenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.csItem1:
                        UserInfoActivity_.intent(CargoSearch.this).userId(cargo.getUserId()).start();
                        return true;
                    case R.id.csItem2:
                        RouteActivity_.intent(CargoSearch.this).startCity(cargo.getLoadingCity()).endCity(cargo.getUnloadingCity()).start();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    @AfterViews
    void init() {
        orderMode = 0;
        intent = getIntent();
        loadCargos();
    }

    private void loadCargos() {
        ParseQuery parseQuery = new ParseQuery("Cargo");
        parseQuery.whereEqualTo("state", 1);
        if (fromCity != null && !fromCity.isEmpty())
            parseQuery.whereEqualTo("loadingCity", fromCity);
        if (toCity != null && !toCity.isEmpty())
            parseQuery.whereEqualTo("unloadingCity", toCity);
        if (bodyType != null && !bodyType.isEmpty())
            parseQuery.whereEqualTo("bodyType", bodyType);
        if (loadType != null && !loadType.isEmpty())
            parseQuery.whereEqualTo("loadType", loadType);
        if (payType != null && !payType.isEmpty())
            parseQuery.whereEqualTo("payType", payType);
        if (fromWeight != null)
            parseQuery.whereGreaterThanOrEqualTo("weight", fromWeight);
        if (toWeight != null)
            parseQuery.whereLessThanOrEqualTo("weight", toWeight);
        if (fromVolume != null)
            parseQuery.whereGreaterThanOrEqualTo("volume", fromVolume);
        if (toVolume != null)
            parseQuery.whereLessThanOrEqualTo("volume", toVolume);
        if (toCity != null && !date.isEmpty())
            parseQuery.whereGreaterThanOrEqualTo("arriveDate", date);

        switch (orderMode) {
            case 0:
                parseQuery.orderByAscending("createdAt");
                break;
            case 1:
                parseQuery.orderByDescending("createdAt");
                break;
            case 2:
                parseQuery.orderByAscending("cost");
                break;
            case 3:
                parseQuery.orderByDescending("cost");
                break;
            case 4:
                parseQuery.orderByAscending("arriveDate");
                break;
            case 5:
                parseQuery.orderByDescending("arriveDate");
                break;
        }
        try {
            List list = parseQuery.find();
            cargos.clear();
            for (Object o : list) {
                cargos.add(new Cargo((ParseObject) o));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cargos.toArray());
        listViewCargo.setAdapter(arrayAdapter);
    }
}
