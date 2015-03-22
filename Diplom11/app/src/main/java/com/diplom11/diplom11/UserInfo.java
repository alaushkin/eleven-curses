package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.diplom11.diplom11.UserTools.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class UserInfo extends ActionBarActivity {
    private TextView usInfo;
    private RatingBar usRatingBar;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
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
        usInfo = (TextView) findViewById(R.id.usInfo);
        usRatingBar = (RatingBar) findViewById(R.id.usRatingBar);

        String userId = getIntent().getStringExtra("userId");
        ParseQuery query = new ParseQuery("UserData");
        query.whereEqualTo("userId", userId);
        try {
            List list = query.find();
            if(list.size() > 0){
                user = new User((ParseObject)list.get(0));
                usInfo.setText(user.toString());
                if(user.getUserId().equals(ParseUser.getCurrentUser().getObjectId())){
                    usRatingBar.setEnabled(false);
                }
            } else{
                usInfo.setText("Пользователь не найден");
                usRatingBar.setEnabled(false);
            }
        } catch (ParseException e) {
            usInfo.setText("Произошла непредвиденная ошибка");
            usRatingBar.setEnabled(false);
            return;
        }

        usRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
    }
}
