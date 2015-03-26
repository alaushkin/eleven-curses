package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends ActionBarActivity {
    @ViewById EditText regLogin, regPassword, regConfirm, regName, regLastName, regMidName;
    @ViewById Spinner regOrg;
    @ViewById TextView regErrorField;

    @AfterViews void init(){
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"ИП","ООО","ЗАО","Физ. лицо"});
        regOrg.setAdapter(adapter);
    }

    @Click void regSubmit() {
        if(!validate()) return;

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
        parseObject.put("sumReit", 0);
        parseObject.put("voteCount", 0);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        parseObject.setACL(acl);
        try {
            parseObject.save();
        } catch (ParseException e) {
            regErrorField.setText("Ошибка подключения к серверу");
            return;
        }
        Intent intent = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
        startActivity(intent);
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
