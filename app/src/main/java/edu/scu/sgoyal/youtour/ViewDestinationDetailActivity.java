package edu.scu.sgoyal.youtour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

;import java.util.ArrayList;

public class ViewDestinationDetailActivity extends MenuActivity
{

    private static final int RECOVERY_REQUEST = 1;
    private String destinationName;
    private  Destination d;
    ListView myListView;
    TextView ratingTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Intent intent = getIntent();
        destinationName = (String) intent.getExtras().get("DESTINATION");
        d = Destination.getDestinationBasedOnName(destinationName);

        final TextView textView = (TextView) findViewById(R.id.textView);

        Log.i(this.getClass().getName(), "Destination = " + d.toString());
        textView.setText(d.getName());

        final TextView textView2= (TextView) findViewById(R.id.textView1);

        textView2.setText(d.getDescription());
        final Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          Intent in = new Intent(ViewDestinationDetailActivity.this, Rating.class);
                                          in.putExtra("DESTINATION" , d );
                                          startActivity(in);
                                      }
                                  }
        );

        ratingTextView = (TextView) findViewById(R.id.textView17);

        Double averageRating = d.getAverageRating();
        Log.i("destination", "No of ratings = " + averageRating);

        if(d.getAverageRating() != 0)
        {
            ratingTextView.setText(String.format( "%.2f", averageRating ));
        }

        //list view
        ArrayList<String> comments = d.getComments();
        Log.i("comments", "No. of comments = " + comments.size());

        for(String d : comments)
        {
            Log.i("comments", "Comment = " + d);

        }
        myListView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                comments);
        myListView.setAdapter(aa);



        YouTubeFragment fragment = new YouTubeFragment(d);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit();
    }


}
