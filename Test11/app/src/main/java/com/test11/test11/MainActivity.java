package com.test11.test11;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private Button yo;
    private int clickCounter;
    private EditText myText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        //view = (ListView)findViewById(R.id.myView);
        yo = (Button) findViewById(R.id.yo);
        myText = (EditText) findViewById(R.id.myText);
        textView = (TextView)findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);
        yo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (clickCounter) {
                    case 0:
                        myText.setText("Ты неочень я пожоще");
                        break;
                    case 1:
                        myText.setText("Терпила");
                        break;
                    case 2:
                        myText.setText("Лалка");
                        myText.setTextSize(72);
                        myText.setEnabled(false);
                        break;
                }
                if(clickCounter == 10){
                    textView.setVisibility(View.VISIBLE);
                }
                clickCounter++;
            }

        });
    }
}
