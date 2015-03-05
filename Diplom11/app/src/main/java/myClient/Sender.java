package myClient;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mak on 04.03.2015.
 */
public class Sender extends AsyncTask<String, Void, String> {
    private InputStream in;
    public String answer = null;
    public boolean flag = false;
    public Sender(InputStream stream){
        in = stream;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            answer = String.valueOf(new BufferedReader(new InputStreamReader(in)).readLine());
            flag=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
