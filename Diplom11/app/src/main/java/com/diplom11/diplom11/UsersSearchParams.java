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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_users_search_params, menu);
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
