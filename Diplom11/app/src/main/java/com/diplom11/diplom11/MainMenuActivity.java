package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.diplom11.diplom11.CargoSearchTools.Dictionary;
import com.parse.ParseException;
import com.parse.ParseQuery;


public class MainMenuActivity extends ActionBarActivity {
    private Button menuItem1;
    private Button menuItem2;
    private Button menuItem3;
    private Button menuItem4;
    private Button menuItem5;
    private Button menuItem6;
    private Button menuItem7;

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
        menuItem1 = (Button) findViewById(R.id.menuItem1);
        menuItem2 = (Button) findViewById(R.id.menuItem2);
        menuItem3 = (Button) findViewById(R.id.menuItem3);
        menuItem4 = (Button) findViewById(R.id.menuItem4);
        menuItem5 = (Button) findViewById(R.id.menuItem5);
        menuItem6 = (Button) findViewById(R.id.menuItem6);
        menuItem7 = (Button) findViewById(R.id.menuItem7);

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

        menuItem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, UsersSearchParams.class);
                startActivity(intent);
            }
        });

        menuItem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, Cabinet.class);
                startActivity(intent);
            }
        });

        menuItem7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, AuthorizationActivity.class);
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
            parseQuery = new ParseQuery("PayType");
            Dictionary.setPayType(parseQuery.find());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
