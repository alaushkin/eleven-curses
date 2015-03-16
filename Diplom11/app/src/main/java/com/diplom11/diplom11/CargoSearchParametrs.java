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

    private void init(){
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

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getBodyTypeArray().toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cspBodyType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getLoadTypeArray().toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cspLoadType.setAdapter(adapter);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Dictionary.getDopArray().toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cspDop.setAdapter(adapter);

        cspSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Intent intent = new Intent(CargoSearchParametrs.this, CargoSearch.class);
                    intent.putExtra("fromCity", cspFromCity.getText().toString());
                    intent.putExtra("toCity", cspToCity.getText().toString());
                    intent.putExtra("bodyType", ((DictPair)cspBodyType.getSelectedItem()).getKey());
                    intent.putExtra("loadType", ((DictPair)cspLoadType.getSelectedItem()).getKey());
                    intent.putExtra("dop", ((DictPair)cspDop.getSelectedItem()).getKey());
                    intent.putExtra("fromWeight", cspFromWeight.getText().toString());
                    intent.putExtra("toWeight", cspToWeight.getText().toString());
                    intent.putExtra("fromVolume", cspFromVolume.getText().toString());
                    intent.putExtra("toVolume", cspToVolume.getText().toString());
                    intent.putExtra("date", cspDate.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validate(){
        return true;
    }
}
