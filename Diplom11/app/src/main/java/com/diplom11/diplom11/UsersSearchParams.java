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


public class UsersSearchParams extends ActionBarActivity {
    private EditText uspUserId;
    private Button uspSearchId;
    private EditText uspLastName;
    private EditText uspFirstName;
    private EditText uspMidName;
    private EditText uspEmail;
    private Spinner uspOrgType;
    private Button uspSearchManualy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_search_params);
        init();
    }

    private void init(){
        uspUserId = (EditText) findViewById(R.id.uspUserId);
        uspSearchId = (Button) findViewById(R.id.uspSearchId);
        uspLastName = (EditText) findViewById(R.id.uspLastName);
        uspFirstName = (EditText) findViewById(R.id.uspFirstName);
        uspMidName = (EditText) findViewById(R.id.uspMidName);
        uspEmail = (EditText) findViewById(R.id.uspEmail);
        uspOrgType = (Spinner) findViewById(R.id.uspOrgType);
        uspSearchManualy = (Button) findViewById(R.id.uspSearchManualy);

        //Для отладки
        uspUserId.setText("2oFf8PPF6T");

        uspSearchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersSearchParams.this, UserInfo.class);
                intent.putExtra("userId", uspUserId.getText().toString());
                startActivity(intent);
            }
        });

        uspSearchManualy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersSearchParams.this, UsersSearch.class);
                intent.putExtra("lastName", uspLastName.getText().toString().trim());
                intent.putExtra("firstName", uspFirstName.getText().toString().trim());
                intent.putExtra("midName", uspMidName.getText().toString().trim());
                intent.putExtra("email", uspEmail.getText().toString().trim());
                intent.putExtra("orgType", (String)uspOrgType.getSelectedItem());
                startActivity(intent);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"", "ИП", "ООО", "ЗАО", "Физ. лицо"});
        uspOrgType.setAdapter(adapter);
    }
}
