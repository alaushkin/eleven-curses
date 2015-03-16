package com.diplom11.diplom11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.diplom11.diplom11.CargoSearchTools.Cargo;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class CargoSearch extends ActionBarActivity {
    private ListView listViewCargo;
    private ArrayList<Cargo> cargos = new ArrayList<>();
    private int orderMode;
    private Button cargoOrderByAddDate;
    private Button cargoOrderByCost;
    private Button cargoOrderByArriveDate;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_search);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cargo_search, menu);
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
        listViewCargo = (ListView) findViewById(R.id.listViewCargo);
        cargoOrderByAddDate = (Button) findViewById(R.id.cargoOrderByAddDate);
        cargoOrderByCost = (Button) findViewById(R.id.cargoOrderByCost);
        cargoOrderByArriveDate = (Button) findViewById(R.id.cargoOrderByArriveDate);
        orderMode = 0;
        intent = getIntent();
        loadCargos();

        cargoOrderByAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderMode == 0){
                    orderMode = 1;
                } else{
                    orderMode = 0;
                }
                loadCargos();
            }
        });

        cargoOrderByCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderMode == 3){
                    orderMode = 2;
                } else{
                    orderMode = 3;
                }
                loadCargos();
            }
        });

        cargoOrderByArriveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderMode == 5){
                    orderMode = 4;
                } else{
                    orderMode = 5;
                }
                loadCargos();
            }
        });
    }

    private void loadCargos(){
        ParseQuery parseQuery = new ParseQuery("Cargo");
        parseQuery.whereEqualTo("state",1);
        parseQuery.whereEqualTo("loadingCity", intent.getStringExtra("fromCity").toUpperCase());
        parseQuery.whereEqualTo("unloadingCity", intent.getStringExtra("toCity").toUpperCase());
        parseQuery.whereEqualTo("bodyType", intent.getStringExtra("bodyType"));
        parseQuery.whereEqualTo("loadType", intent.getStringExtra("loadType"));
        parseQuery.whereEqualTo("dopId", intent.getStringExtra("dop"));
        parseQuery.whereGreaterThanOrEqualTo("weight", Double.parseDouble(intent.getStringExtra("fromWeight")));
        parseQuery.whereLessThanOrEqualTo("weight", Double.parseDouble(intent.getStringExtra("toWeight")));
        parseQuery.whereGreaterThanOrEqualTo("volume", Double.parseDouble(intent.getStringExtra("fromVolume")));
        parseQuery.whereLessThanOrEqualTo("volume", Double.parseDouble(intent.getStringExtra("toVolume")));
        parseQuery.whereGreaterThanOrEqualTo("arriveDate", intent.getStringExtra("date"));

        switch (orderMode){
            case 0:
                parseQuery.orderByAscending("createdAt");
                break;
            case 1:
                parseQuery.orderByDescending("createdAt");
                break;
            case 2:
                parseQuery.orderByAscending("cost");
                break;
            case 3:
                parseQuery.orderByDescending("cost");
                break;
            case 4:
                parseQuery.orderByAscending("arriveDate");
                break;
            case 5:
                parseQuery.orderByDescending("arriveDate");
                break;
        }
        try {
            List list = parseQuery.find();
            cargos.clear();
            for(Object o : list){
                cargos.add(new Cargo((ParseObject) o));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cargos.toArray());
        listViewCargo.setAdapter(arrayAdapter);
    }
}
