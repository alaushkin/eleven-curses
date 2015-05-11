package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.diplom11.diplom11.CarTools.Car;
import com.diplom11.diplom11.CargoSearchTools.Cargo;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_car_search)
@OptionsMenu(R.menu.menu_common)
public class CarSearch extends ActionBarActivity {
    @ViewById ListView listViewCar;

    @Extra String otprDate, arriveDate, fromCity, toCity, bodyType, loadType;
    @Extra Double fromWeight, toWeight, fromVolume, toVolume;

    private ArrayList<Car> cars = new ArrayList<>();
    private Intent intent;
    public PopupMenu popupMenu;

    @ItemClick void listViewCar(final Car car) {
        popupMenu = new PopupMenu(this, listViewCar);
        popupMenu.inflate(R.menu.carsppopupmenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.csItem1:
                        UserInfoActivity_.intent(CarSearch.this).userId(car.getUserId()).start();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    @AfterViews void init() {
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
        if (fromWeight != null)
            parseQuery.whereGreaterThanOrEqualTo("weight", fromWeight);
        if (toWeight != null)
            parseQuery.whereLessThanOrEqualTo("weight", toWeight);
        if (fromVolume != null)
            parseQuery.whereGreaterThanOrEqualTo("volume", fromVolume);
        if (toVolume != null)
            parseQuery.whereLessThanOrEqualTo("volume", toVolume);
        if (toCity != null && !otprDate.isEmpty())
            parseQuery.whereGreaterThanOrEqualTo("otprDate", otprDate);
        if (toCity != null && !otprDate.isEmpty())
            parseQuery.whereLessThanOrEqualTo("arriveDate", arriveDate);
        parseQuery.orderByAscending("otprDate");

        try {
            List list = parseQuery.find();
            cars.clear();
            for (Object o : list) {
                cars.add(new Car((ParseObject) o));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<Object> arrayAdapter;
        if (cars.size() > 0) {
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cars.toArray());
        } else {
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Поиск по заданным параметрам не дал результатов"});
        }
        listViewCar.setAdapter(arrayAdapter);
    }

    @OptionsItem(R.id.toMainMenu)
    void myMethod() {
        MainMenuActivity_.intent(this).start();
    }

}
