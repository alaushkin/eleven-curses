package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.diplom11.diplom11.UserTools.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_users_search)
@OptionsMenu(R.menu.menu_common)
public class UsersSearchActivity extends ActionBarActivity {
    @ViewById ListView usList;
    @Extra String lastName, firstName, midName, email, orgType;

    private ArrayList<User> userList = new ArrayList();

    @AfterViews void init() {
        usList = (ListView) findViewById(R.id.usList);

        loadUsers();

        ArrayAdapter<Object> arrayAdapter;
        if(userList.size() > 0) {
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userList.toArray());
        } else{
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Поиск не дал результатов"});
        }
        usList.setAdapter(arrayAdapter);
    }

    @ItemClick void usList(User user) {
        if(userList.size() <= 0) return;

        UserInfoActivity_.intent(this).userId( user.getUserId() ).start();
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

    @OptionsItem(R.id.toMainMenu)
    void myMethod() {
        MainMenuActivity_.intent(this).start();
    }

}
