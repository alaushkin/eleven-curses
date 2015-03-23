package com.diplom11.diplom11;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.diplom11.diplom11.CargoSearchTools.Cargo;
import com.diplom11.diplom11.UserTools.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class UsersSearch extends ActionBarActivity {
    private ListView usList;
    private ArrayList userList;
    private Intent intent;

    private String lastName;
    private String firstName;
    private String midName;
    private String email;
    private String orgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_search);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_users_search, menu);
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

    private void init() {
        usList = (ListView) findViewById(R.id.usList);
        intent = getIntent();

        lastName = intent.getStringExtra("lastName");
        firstName = intent.getStringExtra("firstName");
        midName = intent.getStringExtra("midName");
        email = intent.getStringExtra("email");
        orgType = intent.getStringExtra("orgType");

        userList = new ArrayList();
        loadUsers();

        usList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(userList.size() > 0) {
                    Intent intent = new Intent(UsersSearch.this, UserInfo.class);
                    intent.putExtra("userId", ((User) userList.get(position)).getUserId());
                    startActivity(intent);
                }
            }
        });
        ArrayAdapter<Object> arrayAdapter;
        if(userList.size() > 0) {
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userList.toArray());
        } else{
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Поиск не дал результатов"});
        }
        usList.setAdapter(arrayAdapter);
    }

    private void loadUsers() {
        ParseQuery parseQuery = new ParseQuery("UserData");
        if (lastName != null && !lastName.isEmpty()) {
            parseQuery.whereContains("lastName", lastName);
        }
        if (firstName != null && !firstName.isEmpty()) {
            parseQuery.whereContains("firstName", firstName);
        }
        if (midName != null && !midName.isEmpty()) {
            parseQuery.whereContains("midName", midName);
        }
        if (email != null && !email.isEmpty()) {
            parseQuery.whereContains("email", email);
        }
        if (orgType != null && !orgType.isEmpty()) {
            parseQuery.whereContains("orgType", orgType);
        }
        try {
            List list = parseQuery.find();
            userList.clear();
            for (Object o : list) {
                userList.add(new User((ParseObject) o));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
