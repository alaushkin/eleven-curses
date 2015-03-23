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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;


public class AddCargo extends ActionBarActivity {
    private EditText addCargoLoadCity;
    private EditText addCargoUnLoadCity;
    private EditText addCargoXSize;
    private EditText addCargoYSize;
    private EditText addCargoZSize;
    private EditText addCargoWeight;
    private EditText addCargoVolume;
    private EditText addCargoCost;
    private EditText addCargoDate;
    private Spinner addCargoBodyType;
    private Spinner addCargoLoadType;
    private Spinner addCargoPayType;
    private Button addCargoButton;
    private TextView addCargoError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cargo);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_cargo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        addCargoLoadCity = (EditText) findViewById(R.id.addCargoLoadCity);
        addCargoUnLoadCity = (EditText) findViewById(R.id.addCargoUnLoadCity);
        addCargoXSize = (EditText) findViewById(R.id.addCargoXSize);
        addCargoYSize = (EditText) findViewById(R.id.addCargoYSize);
        addCargoZSize = (EditText) findViewById(R.id.addCargoZSize);
        addCargoWeight = (EditText) findViewById(R.id.addCargoWeight);
        addCargoVolume = (EditText) findViewById(R.id.addCargoVolume);
        addCargoCost = (EditText) findViewById(R.id.addCargoCost);
        addCargoDate = (EditText) findViewById(R.id.addCargoDate);
        addCargoBodyType = (Spinner) findViewById(R.id.addCargoBodyType);
        addCargoLoadType = (Spinner) findViewById(R.id.addCargoLoadType);
        addCargoPayType = (Spinner) findViewById(R.id.addCargoPayType);
        addCargoButton = (Button) findViewById(R.id.addCargoButton);
        addCargoError = (TextView) findViewById(R.id.addCargoError);

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
        addCargoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
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
                    ParseACL acl = new ParseACL();
                    acl.setPublicReadAccess(true);
                    acl.setPublicWriteAccess(true);
                    parseObject.setACL(acl);
                    try {
                        parseObject.save();
                    } catch (ParseException e) {
                        return;
                    }
                    Intent intent = new Intent(AddCargo.this, MyCargos.class);
                    startActivity(intent);
                } else {
                    addCargoError.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private boolean validate() {
        return !(addCargoLoadCity.getText().toString().isEmpty()
                || addCargoUnLoadCity.getText().toString().isEmpty()
                || addCargoWeight.getText().toString().isEmpty()
                || addCargoVolume.getText().toString().isEmpty());
    }
}
