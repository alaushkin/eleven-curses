package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.diplom11.diplom11.CargoSearchTools.Dictionary;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainMenuActivity extends ActionBarActivity {
    private TextView menuItem1;
    private TextView menuItem2;
    private TextView menuItem3;
    private TextView menuItem4;
    private TextView menuItem5;
    private TextView menuItem6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
        menuItem1 = (TextView) findViewById(R.id.menuItem1);
        menuItem2 = (TextView) findViewById(R.id.menuItem2);
        menuItem3 = (TextView) findViewById(R.id.menuItem3);
        menuItem4 = (TextView) findViewById(R.id.menuItem4);
        menuItem5 = (TextView) findViewById(R.id.menuItem5);
        menuItem6 = (TextView) findViewById(R.id.menuItem6);

        menuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CargoSearchParametrs.class);
                startActivity(intent);
            }
        });

        menuItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, MyCargos.class);
                startActivity(intent);
            }
        });

        try {
            ParseQuery parseQuery = new ParseQuery("dop");
            Dictionary.setDop(parseQuery.find());
            parseQuery = new ParseQuery("BodyType");
            Dictionary.setBodyType(parseQuery.find());
            parseQuery = new ParseQuery("LoadType");
            Dictionary.setLoadType(parseQuery.find());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
