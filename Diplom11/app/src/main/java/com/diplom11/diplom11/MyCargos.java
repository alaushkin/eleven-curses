package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.diplom11.diplom11.CargoSearchTools.Cargo;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_my_cargos)
public class MyCargos extends ActionBarActivity {
    @ViewById ListView myCargos;
    private ArrayList<Cargo> cargos = new ArrayList<>();

    private int selectedCargo = -1;

    @Click void addCargo() {
        Intent intent = new Intent(MyCargos.this, AddCargo.class);
        startActivity(intent);
    }

    @AfterViews void init(){
        ParseQuery parseQuery = new ParseQuery("Cargo");
        parseQuery.whereEqualTo("state",1);
        parseQuery.whereEqualTo("user", ParseUser.getCurrentUser().getObjectId());
        try {
            List list = parseQuery.find();
            for(Object o : list){
                cargos.add(new Cargo((ParseObject) o));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter(this, R.layout.cargi_choise, cargos.toArray());
        myCargos.setAdapter(arrayAdapter);
        myCargos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @ItemClick void myCargos(int position) { selectedCargo = position; }

    @Click void delCargo(){
        if(selectedCargo <= -1) return;
        ParseQuery parseQuery = new ParseQuery("Cargo");
        parseQuery.whereEqualTo("objectId",((Cargo) myCargos.getItemAtPosition(selectedCargo)).getId());
        try {
            ParseObject object = (ParseObject) parseQuery.find().get(0);
            object.delete();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
