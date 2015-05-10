package com.diplom11.diplom11;

import android.content.Intent;
import android.support.annotation.MenuRes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.diplom11.diplom11.CarTools.Car;
import com.diplom11.diplom11.CargoSearchTools.Cargo;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_my_cars)
public class MyCars extends ActionBarActivity {
    ArrayList<Car> cars = new ArrayList();
    private int selectedCar = -1;

    @ViewById ListView myCars;

    @Click void myCarsAddCar() { AddCar_.intent(this).start(); }

    @Click void myCarsDelCar(){
        if(selectedCar <= -1) return;
        ParseQuery parseQuery = new ParseQuery("Car");
        parseQuery.whereEqualTo("objectId",((Car) myCars.getItemAtPosition(selectedCar)).getId());
        try {
            ParseObject object = (ParseObject) parseQuery.find().get(0);
            object.delete();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @AfterViews void init(){
        ParseQuery parseQuery = new ParseQuery("Car");
        parseQuery.whereEqualTo("state",1);
        parseQuery.whereEqualTo("user", ParseUser.getCurrentUser().getObjectId());
        try {
            List list = parseQuery.find();
            for(Object o : list){
                cars.add(new Car((ParseObject) o));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter(this, R.layout.cargi_choise, cars.toArray());
        myCars.setAdapter(arrayAdapter);
        myCars.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @ItemClick void myCars(int position){ selectedCar = position; }
}
