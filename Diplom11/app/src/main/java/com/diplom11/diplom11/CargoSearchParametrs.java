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
import android.widget.Spinner;

import com.diplom11.diplom11.CargoSearchTools.DictPair;
import com.diplom11.diplom11.CargoSearchTools.Dictionary;

import java.util.ArrayList;


public class CargoSearchParametrs extends ActionBarActivity {
    private EditText cspFromCity;
    private EditText cspToCity;
    private Spinner cspBodyType;
    private Spinner cspLoadType;
    private Spinner cspDop;
    private EditText cspFromWeight;
    private EditText cspToWeight;
    private EditText cspFromVolume;
    private EditText cspToVolume;
    private EditText cspDate;
    private Button cspSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_search_parametrs);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cargo_search_parametrs, menu);
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
        cspFromCity = (EditText) findViewById(R.id.cspFromCity);
        cspToCity = (EditText) findViewById(R.id.cspToCity);
        cspBodyType = (Spinner) findViewById(R.id.cspBodyType);
        cspLoadType = (Spinner) findViewById(R.id.cspLoadType);
        cspDop = (Spinner) findViewById(R.id.cspDop);
        cspFromWeight = (EditText) findViewById(R.id.cspFromWeight);
        cspToWeight = (EditText) findViewById(R.id.cspToWeight);
        cspFromVolume = (EditText) findViewById(R.id.cspFromVolume);
        cspToVolume = (EditText) findViewById(R.id.cspToVolume);
        cspDate = (EditText) findViewById(R.id.cspDate);
        cspSubmit = (Button) findViewById(R.id.cspSubmit);

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

        ArrayList dopArray = new ArrayList();
        dopArray.add(new DictPair("", ""));
        dopArray.addAll(Dictionary.getDopArray());
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dopArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cspDop.setAdapter(adapter);

        cspSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Intent intent = new Intent(CargoSearchParametrs.this, CargoSearch.class);
                    intent.putExtra("fromCity", cspFromCity.getText().toString().trim());
                    intent.putExtra("toCity", cspToCity.getText().toString().trim());
                    intent.putExtra("bodyType", ((DictPair) cspBodyType.getSelectedItem()).getKey().trim());
                    intent.putExtra("loadType", ((DictPair) cspLoadType.getSelectedItem()).getKey().trim());
                    intent.putExtra("dop", ((DictPair) cspDop.getSelectedItem()).getKey().trim());
                    intent.putExtra("fromWeight", cspFromWeight.getText().toString().trim());
                    intent.putExtra("toWeight", cspToWeight.getText().toString().trim());
                    intent.putExtra("fromVolume", cspFromVolume.getText().toString().trim());
                    intent.putExtra("toVolume", cspToVolume.getText().toString().trim());
                    intent.putExtra("date", cspDate.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
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
                && ((DictPair) cspDop.getSelectedItem()).getKey().isEmpty());
    }
}
