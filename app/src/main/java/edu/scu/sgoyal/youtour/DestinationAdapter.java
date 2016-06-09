package edu.scu.sgoyal.youtour;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class DestinationAdapter extends ArrayAdapter<Destination>
{
    private final List<Destination> Destinations;
    boolean fav= false;
    public DestinationAdapter(Context context, int resource, List<Destination> Destinations) {
        super(context, resource, Destinations);
        this.Destinations = Destinations;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        if (convertView == null) {
            // This is an expensive operation! Avoid and reuse as much as possible.
            row = inflater.inflate(R.layout.custom_row, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.label);
        textView.setText(Destinations.get(position).getName());

        ImageView imageView = (ImageView) row.findViewById(R.id.icon);

        RatingBar ratingBar = (RatingBar) row.findViewById(R.id.rating);

        try {
            String filename = Destinations.get(position).getImage();
            ratingBar.setRating((float) Destinations.get(position).getAverageRating());
            InputStream inputStream = getContext().getAssets().open(filename);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row;
    }
}
