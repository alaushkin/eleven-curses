package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.diplom11.diplom11.UserTools.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class Cabinet extends ActionBarActivity {
    private TextView cabId;
    private EditText cabEmail;
    private EditText cabFirstName;
    private EditText cabMidName;
    private EditText cabLastName;
    private Spinner cabOrgType;
    private EditText cabPhone;
    private TextView cabReit;
    private Button cabSave;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cabinet, menu);
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
        cabId = (TextView) findViewById(R.id.cabId);
        cabEmail = (EditText) findViewById(R.id.cabEmail);
        cabFirstName = (EditText) findViewById(R.id.cabFirstName);
        cabMidName = (EditText) findViewById(R.id.cabMidName);
        cabLastName = (EditText) findViewById(R.id.cabLastName);
        cabOrgType = (Spinner) findViewById(R.id.cabOrgType);
        cabPhone = (EditText) findViewById(R.id.cabPhone);
        cabReit = (TextView) findViewById(R.id.cabReit);
        cabSave = (Button) findViewById(R.id.cabSave);

        ParseQuery parseQuery = new ParseQuery("UserData");
        parseQuery.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        try {
            user = new User((ParseObject) parseQuery.find().get(0));
        } catch (ParseException e) {
            return;
        }

        cabId.setText(user.getId());
        cabEmail.setText(user.getEmail());
        cabFirstName.setText(user.getFirstName());
        cabMidName.setText(user.getMidName());
        cabLastName.setText(user.getLastName());
        cabPhone.setText(user.getPhone());
        cabReit.setText(user.getReiting());
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{user.getOrgType(), "ИП", "ООО", "ЗАО", "Физ. лицо"});
        cabOrgType.setAdapter(adapter);
    }

    private void save() {

    }

    private boolean validate() {
        return true;
    }
}
