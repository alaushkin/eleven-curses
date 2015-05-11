package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.diplom11.diplom11.CityTools.City;
import com.diplom11.diplom11.DictionaryTools.DictPair;
import com.diplom11.diplom11.DictionaryTools.Dictionary;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_car_search_parameters)
@OptionsMenu(R.menu.menu_common)
public class CarSearchParameters extends ActionBarActivity {
    @ViewById Spinner carSPFromCity, carSPToCity;
    @ViewById Spinner carSPBodyType, carSPLoadType;
    @ViewById
    EditText carSPWeightFrom, carSPWeightTo, carSPVolumeFrom, carSPVolumeTo, carSPOtprDate, carSPArriveDate;

    @AfterViews
    void init() {

        ArrayAdapter<Object> adapter;

        ArrayList bodyTypeArray = new ArrayList();
        bodyTypeArray.add(new DictPair("", ""));
        bodyTypeArray.addAll(Dictionary.getBodyTypeArray());
        adapter = new ArrayAdapter(this, R.layout.my_spinner, bodyTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSPBodyType.setAdapter(adapter);

        ArrayList loadTypeArray = new ArrayList();
        loadTypeArray.add(new DictPair("", ""));
        loadTypeArray.addAll(Dictionary.getLoadTypeArray());
        adapter = new ArrayAdapter(this, R.layout.my_spinner, loadTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSPLoadType.setAdapter(adapter);

        ArrayList fromCityArray = new ArrayList();
        fromCityArray.add(new City());
        fromCityArray.addAll(Dictionary.getCityesArray());
        adapter = new ArrayAdapter(this, R.layout.my_spinner, fromCityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSPFromCity.setAdapter(adapter);

        ArrayList toCityArray = new ArrayList();
        toCityArray.add(new City());
        toCityArray.addAll(Dictionary.getCityesArray());
        adapter = new ArrayAdapter(this, R.layout.my_spinner, toCityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSPToCity.setAdapter(adapter);
    }

    @Click void carSPSubmit() {
        if (!validate()) return;

        CarSearch_.intent(CarSearchParameters.this)
                .fromCity(((City) carSPFromCity.getSelectedItem()).getId())
                .toCity(((City) carSPToCity.getSelectedItem()).getId())
                .bodyType(((DictPair) carSPBodyType.getSelectedItem()).getKey().trim())
                .loadType(((DictPair) carSPLoadType.getSelectedItem()).getKey().trim())
                .otprDate(carSPOtprDate.getText().toString().trim())
                .arriveDate(carSPArriveDate.getText().toString().trim())
                .fromWeight(getDouble(carSPWeightFrom))
                .toWeight(getDouble(carSPWeightTo))
                .fromVolume(getDouble(carSPVolumeFrom))
                .toVolume(getDouble(carSPVolumeTo))
                .start();
    }

    private boolean validate() {
        return !(((City)carSPFromCity.getSelectedItem()).getId().isEmpty()
                && ((City)carSPToCity.getSelectedItem()).getId().isEmpty()
                && carSPWeightFrom.getText().toString().isEmpty()
                && carSPWeightTo.getText().toString().isEmpty()
                && carSPVolumeFrom.getText().toString().isEmpty()
                && carSPVolumeTo.getText().toString().isEmpty()
                && carSPOtprDate.getText().toString().isEmpty()
                && carSPArriveDate.getText().toString().isEmpty()
                && ((DictPair) carSPBodyType.getSelectedItem()).getKey().isEmpty()
                && ((DictPair) carSPLoadType.getSelectedItem()).getKey().isEmpty());
    }

    Double getDouble(TextView textView) {
        try {
            return Double.parseDouble(textView.getText().toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @OptionsItem(R.id.toMainMenu)
    void myMethod() {
        MainMenuActivity_.intent(this).start();
    }

}
