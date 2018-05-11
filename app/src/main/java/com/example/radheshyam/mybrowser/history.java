package com.example.radheshyam.mybrowser;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by Radhe Shyam on 10/25/2017.
 **/
public class history extends AppCompatActivity {

    Button clearhistorybutton;

//    databaseAdapter databaseAdapter;
    databaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.histroy);

        databaseAdapter = new databaseAdapter(this);



        String data = databaseAdapter.getdata();


        clearhistorybutton = (Button) findViewById(R.id.clrhistoryxml);

        String[] favoriteTVShows = {data};
        // The ListAdapter acts as a bridge between the data and each ListItem
        // You fill the ListView with a ListAdapter. You pass it a context represented by
        // this. A Context provides access to resources you need.
        // android.R.layout.simple_list_item_1 is one of the resources needed.
        // It is a predefined layout provided by Android that stands in as a default

        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                favoriteTVShows);
    // ListViews display data in a scrollable list
        ListView theListView = (ListView) findViewById(R.id.listViewHistory);

        // Tells the ListView what data to use
        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String tvShowPicked = "You selected " +
                        String.valueOf(adapterView.getItemAtPosition(i));

                Toast.makeText(history.this, "Nothing is here", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void clearshistory(View view) {


        databaseAdapter.deletedata();


        Toast.makeText(history.this,"History is cleared Now", Toast.LENGTH_SHORT).show();

    }
}
