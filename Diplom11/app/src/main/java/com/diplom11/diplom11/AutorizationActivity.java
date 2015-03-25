package com.diplom11.diplom11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import bolts.Task;

public class AutorizationActivity extends ActionBarActivity {
    private EditText login;
    private EditText password;
    private TextView errorField;
    private TextView registration;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_autorization, menu);
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
        SharedPreferences sPref;
        sPref = getPreferences(MODE_PRIVATE);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        errorField = (TextView) findViewById(R.id.errorField);
        registration = (TextView) findViewById(R.id.registration);
        signIn = (Button) findViewById(R.id.signIn);
        login.setText(sPref.getString("login", ""));
        password.setText(sPref.getString("password", ""));

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authentication()) {
                    SharedPreferences sPref;
                    sPref = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString("login", login.getText().toString());
                    ed.putString("password", password.getText().toString());
                    ed.commit();

                    Intent intent = new Intent(AutorizationActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }

            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutorizationActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onBackPressed() {
    }
}
