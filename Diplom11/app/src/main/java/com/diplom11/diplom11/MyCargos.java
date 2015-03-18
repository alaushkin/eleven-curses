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

import java.util.ArrayList;
import java.util.List;


public class MyCargos extends ActionBarActivity {
    private Button addCargo;
    private Button delCargo;
    private ListView myCargos;
    private ArrayList<Cargo> cargos = new ArrayList<>();

    private int selectedCargo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cargos);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_cargos, menu);
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
        addCargo = (Button) findViewById(R.id.addCargo);
        delCargo = (Button) findViewById(R.id.delCargo);
        myCargos = (ListView) findViewById(R.id.myCargos);

        addCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCargos.this, AddCargo.class);
                startActivity(intent);
            }
        });
        delCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedCargo();
            }
        });

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

        myCargos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCargo = position;
            }
        });
    }

    private void deleteSelectedCargo(){
        if(selectedCargo > -1) {
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
            //ob.re
            //(Cargo) myCargos.getSelectedItem();
        }
    }
}
