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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main_menu)
public class MainMenuActivity extends ActionBarActivity {

    @Click void menuItem1() {
        Intent intent = new Intent(MainMenuActivity.this, CargoSearchParametrs.class);
        startActivity(intent);
    }

    @Click void menuItem4() {
        Intent intent = new Intent(MainMenuActivity.this, MyCargos.class);
        startActivity(intent);
    }

    @Click void menuItem5(){
        Intent intent = new Intent(MainMenuActivity.this, UsersSearchParams.class);
        startActivity(intent);
    }

    @Click void menuItem6() {
        Intent intent = new Intent(MainMenuActivity.this, Cabinet.class);
        startActivity(intent);
    }

    @Click void menuItem7() {
        Intent intent = new Intent(MainMenuActivity.this, AuthorizationActivity.class);
        startActivity(intent);
    }
    @AfterViews void init(){
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

    @Override public void onBackPressed() { }
}
