package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.diplom11.diplom11.CityTools.City;
import com.diplom11.diplom11.DictionaryTools.DictPair;
import com.diplom11.diplom11.DictionaryTools.Dictionary;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.math.BigDecimal;

@EActivity(R.layout.activity_add_car)
@OptionsMenu(R.menu.menu_common)
public class AddCar extends ActionBarActivity {
    @ViewById EditText addCarWeight, addCarVolume, addCarArriveDate, addCarOtprDate;
    @ViewById Spinner addCarLoadCity, addCarUnLoadCity;
    @ViewById Spinner addCarBodyType, addCarLoadType;


    @AfterViews
    void init() {
        ArrayAdapter<Object> adapter;

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getBodyTypeArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCarBodyType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getLoadTypeArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCarLoadType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getCityesArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCarLoadCity.setAdapter(adapter);

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getCityesArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCarUnLoadCity.setAdapter(adapter);
    }

    @Click void addCargoButton(){
        ParseObject parseObject = new ParseObject("Car");
        parseObject.put("loadingCity", ((City) addCarLoadCity.getSelectedItem()).getId());
        parseObject.put("unloadingCity", ((City) addCarUnLoadCity.getSelectedItem()).getId());
        parseObject.put("bodyType", ((DictPair) addCarBodyType.getSelectedItem()).getKey());
        parseObject.put("loadType", ((DictPair) addCarLoadType.getSelectedItem()).getKey());
        if (addCarWeight.getText().toString() != null && !addCarWeight.getText().toString().isEmpty())
            parseObject.put("weight", new BigDecimal(addCarWeight.getText().toString()));
        if (addCarVolume.getText().toString() != null && !addCarVolume.getText().toString().isEmpty())
            parseObject.put("volume", new BigDecimal(addCarVolume.getText().toString()));
        parseObject.put("arriveDate", addCarArriveDate.getText().toString());
        parseObject.put("otprDate", addCarOtprDate.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getObjectId());
        parseObject.put("state", 1);

        try {
            parseObject.save();
        } catch (ParseException e) {
            return;
        }
        MyCars_.intent(this).start();
    }

    @OptionsItem(R.id.toMainMenu)
    void myMethod() {
        MainMenuActivity_.intent(this).start();
    }

}
