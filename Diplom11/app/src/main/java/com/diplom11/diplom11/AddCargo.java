package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
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

@EActivity(R.layout.activity_add_cargo)
@OptionsMenu(R.menu.menu_common)
public class AddCargo extends ActionBarActivity {
    @ViewById
    EditText addCargoXSize, addCargoYSize, addCargoZSize,
            addCargoWeight, addCargoVolume, addCargoCost, addCargoDate;
    @ViewById
    Spinner addCargoLoadCity, addCargoUnLoadCity;
    @ViewById
    Spinner addCargoBodyType, addCargoLoadType, addCargoPayType;
    @ViewById
    TextView addCargoError;


    @AfterViews
    void init() {
        ArrayAdapter<Object> adapter;

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getBodyTypeArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoBodyType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getLoadTypeArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoLoadType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getPayTypeArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoPayType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getCityesArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoLoadCity.setAdapter(adapter);

        adapter = new ArrayAdapter(this, R.layout.my_spinner, Dictionary.getCityesArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoUnLoadCity.setAdapter(adapter);

        addCargoError.setVisibility(View.INVISIBLE);
    }

    @Click
    void addCargoButton() {
        if (!validate()) {
            addCargoError.setVisibility(View.VISIBLE);
            return;
        }

        ParseObject parseObject = new ParseObject("Cargo");
        parseObject.put("loadingCity", ((City) addCargoLoadCity.getSelectedItem()).getId());
        parseObject.put("unloadingCity", ((City) addCargoUnLoadCity.getSelectedItem()).getId());
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
        return !(addCargoWeight.getText().toString().isEmpty()
                || addCargoVolume.getText().toString().isEmpty());
    }

    @OptionsItem(R.id.toMainMenu)
    void myMethod() {
        MainMenuActivity_.intent(this).start();
    }

}
