package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.diplom11.diplom11.UserTools.User;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class UserInfo extends ActionBarActivity {
    private TextView usInfo;
    private RatingBar usRatingBar;
    private Button usVote;
    //Assessment
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
        usVote = (Button) findViewById(R.id.usVote);

        final String userId = getIntent().getStringExtra("userId");
        ParseQuery query = new ParseQuery("UserData");
        query.whereEqualTo("userId", userId);
        try {
            List list = query.find();
            if(list.size() > 0){
                user = new User((ParseObject)list.get(0));
                usInfo.setText(user.toString());
                if(user.getUserId().equals(ParseUser.getCurrentUser().getObjectId())){
                    usRatingBar.setEnabled(false);
                    usVote.setEnabled(false);
                }
                getRating();
            } else{
                usInfo.setText("Пользователь не найден");
                usRatingBar.setEnabled(false);
                usVote.setEnabled(false);
            }
        } catch (ParseException e) {
            usInfo.setText("Произошла непредвиденная ошибка");
            usRatingBar.setEnabled(false);
            usVote.setEnabled(false);
            return;
        }

        usVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject parseObject = new ParseObject("Assessment");
                parseObject.put("User1", ParseUser.getCurrentUser().getObjectId());
                parseObject.put("User2", user.getUserId());
                parseObject.put("value", usRatingBar.getRating());
                ParseACL acl = new ParseACL();
                acl.setPublicReadAccess(true);
                acl.setPublicWriteAccess(true);
                try {
                    parseObject.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ParseQuery parseQuery = new ParseQuery("UserData");
                parseQuery.whereEqualTo("userId", user.getUserId());
                try {
                    List list = parseQuery.find();
                    if(list.size() > 0){
                        ParseObject usr = (ParseObject)list.get(0);
                        double sumReit = usr.getDouble("sumReit");
                        int count = usr.getInt("voteCount");
                        sumReit += usRatingBar.getRating()*2;
                        count++;
                        usr.put("sumReit", sumReit);
                        usr.put("voteCount", count);
                        usr.save();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                startActivity(getIntent());
            }
        });
    }

    public void getRating(){
        ParseQuery parseQuery = new ParseQuery("Assessment");
        parseQuery.whereEqualTo("User1", ParseUser.getCurrentUser().getObjectId());
        parseQuery.whereEqualTo("User2", user.getUserId());
        try {
            List list = parseQuery.find();
            if(list.size() > 0){
                usRatingBar.setEnabled(false);
                usVote.setEnabled(false);
                Double value = ((ParseObject)list.get(0)).getDouble("value");
                usRatingBar.setRating(value.floatValue());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
