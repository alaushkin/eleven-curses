package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_users_search_params)
public class UsersSearchParamsActivity extends ActionBarActivity {
    @ViewById EditText uspUserId, uspLastName, uspFirstName, uspMidName, uspEmail;
    @ViewById Spinner uspOrgType;

    @AfterViews void init(){
        //Для отладки
        uspUserId.setText("2oFf8PPF6T");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"", "ИП", "ООО", "ЗАО", "Физ. лицо"});
        uspOrgType.setAdapter(adapter);
    }

    @Click void uspSearchId() {
        UserInfoActivity_.intent(this).userId( uspUserId.getText().toString() ).start();
    }

    @Click void uspSearchManualy() {
        UsersSearchActivity_.intent(this)
                .lastName   (uspLastName.getText().toString().trim())
                .firstName  (uspFirstName.getText().toString().trim())
                .midName   (uspMidName.getText().toString().trim())
                .email      (uspEmail.getText().toString().trim())
                .orgType    ( (String)uspOrgType.getSelectedItem())
                .start();
    }
}
