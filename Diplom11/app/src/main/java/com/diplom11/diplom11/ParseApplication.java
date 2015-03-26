package com.diplom11.diplom11;

import android.app.Application;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
          //Parse.initialize(this);
        Parse.initialize(this, "4ha34zg9TAM31ZK8QFTqvD32v3BY7Uw2hL9B1ytS", "mKkb3WKKL1lvbrNPU7yrI5rr9oz2y6sQ4lCxs8q7");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicWriteAccess(true);
        defaultACL.setPublicReadAccess(true);

        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
