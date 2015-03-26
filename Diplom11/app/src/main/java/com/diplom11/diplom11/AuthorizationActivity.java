package com.diplom11.diplom11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_autorization)
public class AuthorizationActivity extends ActionBarActivity {
    @ViewById EditText login, password;
    @ViewById TextView errorField;

    @AfterViews void init(){
        SharedPreferences sPref;
        sPref = getPreferences(MODE_PRIVATE);
        login.setText(sPref.getString("login", ""));
        password.setText(sPref.getString("password", ""));

    }

    @Click void registration() { RegistrationActivity_.intent(this).start(); }

    @Click void signIn(){
        if (!authentication()) return;

        SharedPreferences sPref;
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("login", login.getText().toString());
        ed.putString("password", password.getText().toString());
        ed.commit();

        MainMenuActivity_.intent(this).start();
    }

    private boolean authentication(){
        if(login.getText().toString().equals("") || password.getText().toString().equals("")){
            errorField.setText("Введите логин/пароль.");
            return false;
        }
        try {
            ParseUser.logIn(login.getText().toString(), password.getText().toString());
        } catch (ParseException e) {
            errorField.setText("Неправильный логин/пароль.");
            return false;
        }
        return true;
    }

    @Override public void onBackPressed() {}
}
