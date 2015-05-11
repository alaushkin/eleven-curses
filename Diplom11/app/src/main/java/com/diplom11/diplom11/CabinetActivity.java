package com.diplom11.diplom11;

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

import com.diplom11.diplom11.UserTools.User;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_cabinet)
@OptionsMenu(R.menu.menu_common)
public class CabinetActivity extends ActionBarActivity {
    @ViewById TextView cabId, cabReit, cabError;
    @ViewById EditText cabEmail, cabFirstName, cabMidName, cabLastName, cabPhone, cabNewPassword;
    @ViewById Spinner cabOrgType;

    private User user;
    private ParseObject userObject;

    @AfterViews void init() {

        ParseQuery parseQuery = new ParseQuery("UserData");
        parseQuery.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        try {
            userObject = (ParseObject) parseQuery.find().get(0);
            user = new User(userObject);
        } catch (ParseException e) {
            return;
        }

        cabId.setText(user.getUserId());
        cabEmail.setText(user.getEmail());
        cabFirstName.setText(user.getFirstName());
        cabMidName.setText(user.getMidName());
        cabLastName.setText(user.getLastName());
        cabPhone.setText(user.getPhone());
        cabReit.setText(user.getReiting());

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{user.getOrgType(), "ИП", "ООО", "ЗАО", "Физ. лицо"});
        cabOrgType.setAdapter(adapter);
    }

    @Click void cabSave() {
        if(!validate())return;

        userObject.put("email", cabEmail.getText().toString());
        userObject.put("firstName", cabFirstName.getText().toString());
        userObject.put("lastName", cabLastName.getText().toString());
        userObject.put("midName", cabMidName.getText().toString());
        userObject.put("orgType", cabOrgType.getSelectedItem());
        userObject.put("phone", cabPhone.getText().toString());
        if(!cabNewPassword.getText().toString().isEmpty()){
            ParseUser.getCurrentUser().setPassword(cabNewPassword.getText().toString());
        }
        try {
            userObject.save();
            ParseUser.getCurrentUser().save();
        } catch (ParseException e) {
            cabError.setText("Ошибка сохранения данных");
            return;
        }
        cabError.setText("Данные успешно сохранены");
    }

    private boolean validate() {
        if(!cabNewPassword.getText().toString().isEmpty() && cabNewPassword.getText().toString().length() < 6){
            cabError.setText("Минимальная длина пароля 6 символов");
            return false;
        }
        return true;
    }

    @OptionsItem(R.id.toMainMenu)
    void myMethod() {
        MainMenuActivity_.intent(this).start();
    }

}
