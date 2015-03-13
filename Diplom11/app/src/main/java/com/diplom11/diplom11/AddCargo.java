package com.diplom11.diplom11;

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
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.math.BigDecimal;
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
    private Spinner addCargoDop;
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

    private void init(){
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
        addCargoDop = (Spinner) findViewById(R.id.addCargoDop);
        addCargoButton = (Button) findViewById(R.id.addCargoButton);
        addCargoError = (TextView) findViewById(R.id.addCargoError);

        ArrayAdapter<Object> adapter;

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getBodyTypeArray().toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoBodyType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getLoadTypeArray().toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoLoadType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getDopArray().toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCargoDop.setAdapter(adapter);

        addCargoError.setVisibility(View.INVISIBLE);
        addCargoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    ParseObject parseObject = new ParseObject("Cargo");
                    parseObject.put("loadingCity", addCargoLoadCity.getText().toString());
                    parseObject.put("unloadingCity", addCargoUnLoadCity.getText().toString());
                    parseObject.put("bodyType", ((DictPair)addCargoBodyType.getSelectedItem()).getKey());
                    parseObject.put("loadType", ((DictPair)addCargoLoadType.getSelectedItem()).getKey());
                    parseObject.put("dopId", ((DictPair)addCargoDop.getSelectedItem()).getKey());
                    parseObject.put("xSize", new BigDecimal(addCargoXSize.getText().toString()));
                    parseObject.put("ySize", new BigDecimal(addCargoYSize.getText().toString()));
                    parseObject.put("zSize", new BigDecimal(addCargoZSize.getText().toString()));
                    parseObject.put("weight", new BigDecimal(addCargoWeight.getText().toString()));
                    parseObject.put("volume", new BigDecimal(addCargoVolume.getText().toString()));
                    parseObject.put("cost", new BigDecimal(addCargoCost.getText().toString()));
                    parseObject.put("arriveDate", new Date());
                    parseObject.put("user", ParseUser.getCurrentUser().getObjectId());
                    parseObject.put("state", 1);
                    parseObject.setACL(new ParseACL(ParseUser.getCurrentUser()));
                    parseObject.saveInBackground();
                } else {
                    addCargoError.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private boolean validate(){
        return !(addCargoLoadCity.getText().toString().isEmpty()
                || addCargoUnLoadCity.getText().toString().isEmpty()
                || addCargoXSize.getText().toString().isEmpty()
                || addCargoYSize.getText().toString().isEmpty()
                || addCargoZSize.getText().toString().isEmpty()
                || addCargoWeight.getText().toString().isEmpty()
                || addCargoVolume.getText().toString().isEmpty()
                || addCargoCost.getText().toString().isEmpty()
                || addCargoDate.getText().toString().isEmpty());
    }
}
