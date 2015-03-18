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
import android.widget.TextView;

import com.diplom11.diplom11.CargoSearchTools.Dictionary;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class RegistrationActivity extends ActionBarActivity {
    private EditText regLogin;
    private EditText regPassword;
    private EditText regConfirm;
    private EditText regName;
    private EditText regLastName;
    private EditText regMidName;
    private Spinner regOrg;
    private TextView regErrorField;
    private Button regSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
        regLogin = (EditText) findViewById(R.id.regLogin);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regConfirm = (EditText) findViewById(R.id.regConfirm);
        regName = (EditText) findViewById(R.id.regName);
        regLastName = (EditText) findViewById(R.id.regLastName);
        regMidName = (EditText) findViewById(R.id.regLastName);
        regOrg = (Spinner) findViewById(R.id.regOrg);
        regErrorField = (TextView) findViewById(R.id.regErrorField);
        regSubmit = (Button) findViewById(R.id.regSubmit);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"ИП","ООО","ЗАО","Физ. лицо"});
        regOrg.setAdapter(adapter);

        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    ParseUser user = new ParseUser();
                    user.setUsername(regLogin.getText().toString());
                    user.setPassword(regPassword.getText().toString());
                    try {
                        user.signUp();
                    } catch (ParseException e) {
                        regErrorField.setText("Такой логин уже существует");
                        return;
                    }
                    ParseObject parseObject = new ParseObject("UserData");
                    parseObject.put("userId", user.getObjectId());
                    parseObject.put("email", regLogin.getText().toString());
                    parseObject.put("firstName", regName.getText().toString());
                    parseObject.put("lastName", regLastName.getText().toString());
                    parseObject.put("midName", regMidName.getText().toString());
                    parseObject.put("orgType", regOrg.getSelectedItem());
                    parseObject.setACL(new ParseACL(ParseUser.getCurrentUser()));
                    try {
                        parseObject.save();
                    } catch (ParseException e) {
                        regErrorField.setText("Ошибка подключения к серверу");
                        return;
                    }
                    Intent intent = new Intent(RegistrationActivity.this, AutorizationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validate(){
        if(regLogin.getText().toString().isEmpty()
                || regPassword.getText().toString().isEmpty()
                || regConfirm.getText().toString().isEmpty()
                || regName.getText().toString().isEmpty()
                || regLastName.getText().toString().isEmpty()
                || regMidName.getText().toString().isEmpty()){
            regErrorField.setText("Заполните все поля");
            return false;
        }
        if(!regLogin.getText().toString().contains("@")){
            regErrorField.setText("Введен некорректный логин");
            return false;
        }
        if(regPassword.getText().toString().length() < 6){
            regErrorField.setText("Минимальная длина пароля 6 символов");
            return false;
        }
        if(!regPassword.getText().toString().equals(regConfirm.getText().toString())){
            regErrorField.setText("Пароли не совпадают");
            return false;
        }

        return true;
    }
}
