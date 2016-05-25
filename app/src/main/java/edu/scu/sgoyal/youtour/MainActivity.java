package edu.scu.sgoyal.youtour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Menu_Activity {
static List<Destination> destinations;
    public final static String POSITION_ID= "rama";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        destinations = MapsActivity.destinations;
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new Destination_Adapter(this, R.layout.custom_row, destinations));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, View_destination.class);
                intent.putExtra(POSITION_ID, position);
                startActivity(intent);

            }
        });


    }}

