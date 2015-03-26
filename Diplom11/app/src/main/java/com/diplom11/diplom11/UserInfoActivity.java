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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_user_info)
public class UserInfoActivity extends ActionBarActivity {
    @ViewById TextView usInfo;
    @ViewById RatingBar usRatingBar;
    @ViewById Button usVote;
    //Assessment
    private User user;

    @Extra  String  userId;

    @AfterViews void init(){
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

    }

    @Click void usVote() {
        ParseObject parseObject = new ParseObject("Assessment");
        parseObject.put("User1", ParseUser.getCurrentUser().getObjectId());
        parseObject.put("User2", user.getUserId());
        parseObject.put("value", usRatingBar.getRating());
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
