package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.diplom11.diplom11.CargoSearchTools.DictPair;
import com.diplom11.diplom11.CargoSearchTools.Dictionary;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@EActivity(R.layout.activity_add_cargo)
public class AddCargo extends ActionBarActivity {
    @ViewById EditText addCargoLoadCity, addCargoUnLoadCity, addCargoXSize,
            addCargoYSize, addCargoZSize, addCargoWeight,
            addCargoVolume, addCargoCost, addCargoDate;
    @ViewById Spinner addCargoBodyType, addCargoLoadType, addCargoPayType;
    @ViewById TextView addCargoError;


    @AfterViews void init() {
        ArrayAdapter<Object> adapter;

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getBodyTypeArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoBodyType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getLoadTypeArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoLoadType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getPayTypeArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoPayType.setAdapter(adapter);

        addCargoError.setVisibility(View.INVISIBLE);
    }

    @Click void addCargoButton() {
        if (!validate()) {
            addCargoError.setVisibility(View.VISIBLE);
            return;
        }

        ParseObject parseObject = new ParseObject("Cargo");
        parseObject.put("loadingCity", addCargoLoadCity.getText().toString().toUpperCase());
        parseObject.put("unloadingCity", addCargoUnLoadCity.getText().toString().toUpperCase());
        parseObject.put("bodyType", ((DictPair) addCargoBodyType.getSelectedItem()).getKey());
        parseObject.put("loadType", ((DictPair) addCargoLoadType.getSelectedItem()).getKey());
        parseObject.put("payType", ((DictPair) addCargoPayType.getSelectedItem()).getKey());
        if (addCargoXSize.getText().toString() != null && !addCargoXSize.getText().toString().isEmpty())
            parseObject.put("xSize", new BigDecimal(addCargoXSize.getText().toString()));
        if (addCargoYSize.getText().toString() != null && !addCargoYSize.getText().toString().isEmpty())
            parseObject.put("ySize", new BigDecimal(addCargoYSize.getText().toString()));
        if (addCargoZSize.getText().toString() != null && !addCargoZSize.getText().toString().isEmpty())
            parseObject.put("zSize", new BigDecimal(addCargoZSize.getText().toString()));
        if (addCargoWeight.getText().toString() != null && !addCargoWeight.getText().toString().isEmpty())
            parseObject.put("weight", new BigDecimal(addCargoWeight.getText().toString()));
        if (addCargoVolume.getText().toString() != null && !addCargoVolume.getText().toString().isEmpty())
            parseObject.put("volume", new BigDecimal(addCargoVolume.getText().toString()));
        if (addCargoCost.getText().toString() != null && !addCargoCost.getText().toString().isEmpty())
            parseObject.put("cost", new BigDecimal(addCargoCost.getText().toString()));
        parseObject.put("arriveDate", addCargoDate.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getObjectId());
        parseObject.put("state", 1);

        try {
            parseObject.save();
        } catch (ParseException e) {
            return;
        }
        MyCargos_.intent(this).start();
    }

    private boolean validate() {
        return !(addCargoLoadCity.getText().toString().isEmpty()
                || addCargoUnLoadCity.getText().toString().isEmpty()
                || addCargoWeight.getText().toString().isEmpty()
                || addCargoVolume.getText().toString().isEmpty());
    }
}
