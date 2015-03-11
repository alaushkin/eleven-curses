package com.diplom11.diplom11;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.diplom11.diplom11.CargoSearchTools.Dictionary;


public class AddCargo extends ActionBarActivity {
    private ExpandableListView bodyTypeList;
    private ExpandableListView loadTypeList;
    private ExpandableListView dopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cargo);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_cargo, menu);
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
//        bodyTypeList =  (ExpandableListView) findViewById(R.id.bodyTypeList);
//        loadTypeList =  (ExpandableListView) findViewById(R.id.loadTypeList);
//        dopList =  (ExpandableListView) findViewById(R.id.dopList);

//        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
//                this,
//                groupData,
//                android.R.layout.simple_expandable_list_item_1,
//                groupFrom,
//                groupTo,
//                childData,
//                android.R.layout.simple_list_item_1,
//                childFrom,
////                childTo);
//        ArrayAdapter<Object> arrayAdapter;
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Dictionary.getBodyTypeArray().toArray());
//        bodyTypeList.setAdapter(arrayAdapter);
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Dictionary.getLoadTypeArray().toArray());
//        loadTypeList.setAdapter(arrayAdapter);
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Dictionary.getDopArray().toArray());
//        dopList.setAdapter(arrayAdapter);
    }
}
