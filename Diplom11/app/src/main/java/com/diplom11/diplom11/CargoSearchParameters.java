package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.diplom11.diplom11.CargoSearchTools.DictPair;
import com.diplom11.diplom11.CargoSearchTools.Dictionary;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;


@EActivity(R.layout.activity_cargo_search_parametrs)
public class CargoSearchParameters extends ActionBarActivity {
    @ViewById EditText cspFromCity, cspToCity;
    @ViewById Spinner cspBodyType, cspLoadType, cspPayType;
    @ViewById EditText cspFromWeight, cspToWeight, cspFromVolume, cspToVolume, cspDate;

    @AfterViews void init() {

        ArrayAdapter<Object> adapter;

        ArrayList bodyTypeArray = new ArrayList();
        bodyTypeArray.add(new DictPair("", ""));
        bodyTypeArray.addAll(Dictionary.getBodyTypeArray());
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bodyTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cspBodyType.setAdapter(adapter);

        ArrayList loadTypeArray = new ArrayList();
        loadTypeArray.add(new DictPair("", ""));
        loadTypeArray.addAll(Dictionary.getLoadTypeArray());
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, loadTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cspLoadType.setAdapter(adapter);

        ArrayList payTypeArray = new ArrayList();
        payTypeArray.add(new DictPair("", ""));
        payTypeArray.addAll(Dictionary.getPayTypeArray());
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, payTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cspPayType.setAdapter(adapter);

    }

    @Click  void cspSubmit() {
        if (!validate()) return;

        CargoSearch_.intent(CargoSearchParameters.this)
            .fromCity(cspFromCity.getText().toString().trim())
            .toCity     ( cspToCity.getText().toString().trim()     )
            .bodyType   ( ((DictPair) cspBodyType.getSelectedItem()).getKey().trim() )
            .loadType   ( ((DictPair) cspLoadType.getSelectedItem()).getKey().trim() )
            .payType    ( ((DictPair) cspPayType.getSelectedItem()).getKey().trim() )
            .date       ( cspDate.getText().toString().trim())
            .fromWeight ( getDouble(cspFromWeight ) )
            .toWeight   ( getDouble(cspToWeight) )
            .fromVolume ( getDouble(cspFromVolume) )
            .toVolume   ( getDouble(cspToVolume) )
            .start();
    }

    private boolean validate() {
        return !(cspFromCity.getText().toString().isEmpty()
                && cspToCity.getText().toString().isEmpty()
                && cspFromWeight.getText().toString().isEmpty()
                && cspToWeight.getText().toString().isEmpty()
                && cspFromVolume.getText().toString().isEmpty()
                && cspToVolume.getText().toString().isEmpty()
                && cspDate.getText().toString().isEmpty()
                && ((DictPair) cspBodyType.getSelectedItem()).getKey().isEmpty()
                && ((DictPair) cspLoadType.getSelectedItem()).getKey().isEmpty()
                && ((DictPair) cspPayType.getSelectedItem()).getKey().isEmpty());
    }

    Double  getDouble(TextView textView) {
        try {
            return Double.parseDouble(textView.getText().toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
