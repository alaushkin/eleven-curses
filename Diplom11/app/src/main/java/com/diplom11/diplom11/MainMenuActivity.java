package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import com.diplom11.diplom11.DictionaryTools.Dictionary;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_main_menu)
public class MainMenuActivity extends ActionBarActivity {

    @Click void menuItem1() { CargoSearchParameters_.intent(this).start(); }
    @Click void menuItem2() { Intent intent1 = new Intent(MainMenuActivity.this, MapsActivity.class);
        startActivity(intent1);}
    @Click void menuItem4() { MyCargos_.intent(this).start(); }
    @Click void menuItem5() { UsersSearchParamsActivity_.intent(this).start(); }

    @Click void menuItem6() { CabinetActivity_.intent(this).start(); }

    @Click void menuItem7() {
        ParseUser.getCurrentUser().logOut();
        AuthorizationActivity_.intent(this).start();
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
            parseQuery = new ParseQuery("Cityes");
            Dictionary.setCityes(parseQuery.find());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override public void onBackPressed() { }
}
