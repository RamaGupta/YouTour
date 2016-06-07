package edu.scu.sgoyal.youtour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class Rating extends MenuActivity
{

    EditText comment;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Intent intent = getIntent();
        final Destination d = (Destination) intent.getExtras().get("DESTINATION");
        final Destination newD = Destination.getDestinationBasedOnName(d.getName());
        Log.i(this.getClass().getName(), "============= coming to rating  "+d.getName());

        comment = (EditText) findViewById(R.id.commentEditText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);

        Button submitComment = (Button) findViewById(R.id.submitComment);
        Button skip = (Button) findViewById(R.id.skip);

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(this.getClass().getName(), "============= On click  ");

                String newComment = comment.getText().toString();

                newD.getComments().add(newComment);

                int index = Destination.getDestinations().indexOf(Destination.getDestinationBasedOnName(d.getName()));
                Log.i("Index of Sdsfai", "=============INdex = "+ index + newD.getName());

                MapsActivity.myFirebaseRef.child(String.valueOf(index))
                        .child("comments").setValue(newD.getComments());



                Double newRating = Double.parseDouble(ratingBar.getRating() + "");
                Log.i("NewRating" , "Rating = " + newRating);
                Double averageRating = newD.getAverageRating();
                if(averageRating == 0)
                {
                    newD.setAverageRating(newRating);
                }
                else
                {
                    int noOfRatings = newD.getRatings().size();
                    double totalRating = averageRating * noOfRatings;
                    totalRating += newRating;
                    averageRating = totalRating / (noOfRatings + 1);
                    newD.setAverageRating(averageRating);
                }

                Log.i("NewRating" , "Rating = " + averageRating);
                newD.getRatings().add(newRating);

                MapsActivity.myFirebaseRef.child(String.valueOf(index))
                        .child("ratings").setValue(newD.getRatings());

                MapsActivity.myFirebaseRef.child(String.valueOf(index))
                        .child("averageRating").setValue(newD.getAverageRating());

                Intent i = new Intent(Rating.this.getApplicationContext(), MapsActivity.class);
                startActivity(i);

            }
        });

        skip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Rating.this.getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });

    }
}
