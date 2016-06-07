package edu.scu.sgoyal.youtour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by shubhamgoyal on 5/21/16.
 */
public class ViewDestination extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b)
    {

    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {

    }

//    private static final int RECOVERY_REQUEST = 1;
//    private YouTubePlayerView youTubeView;
//    private YouTubePlayer player;
//    private String destinationName;
//
//    private  Destination d;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout);
//        Intent intent = getIntent();
//        destinationName = (String) intent.getExtras().get("DESTINATION");
//        d = Destination.getDestinationBasedOnName(destinationName);
//
////        Log.i("sgoyal" , "bkjdsfbkjsdf");
//        final TextView textView = (TextView) findViewById(R.id.textView);
//
//
//        textView.setText(d.getName());
//
//        final TextView textView2= (TextView) findViewById(R.id.textView1);
//
//       textView2.setText(d.getDescription());
//        final Button button = (Button)findViewById(R.id.button1);
//        button.setOnClickListener(new View.OnClickListener() {
//                                      @Override
//                                      public void onClick(View v) {
//
//                                          Intent in=new Intent(ViewDestination.this, MapsActivity.class);
//                                          startActivity(in);
//                                      }
//                                  }
//        );
//        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
//        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
//
//
//    }
//
//    @Override
//    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
//        this.player = player;
//
//        if (!wasRestored) {
//            player.cueVideo(d.getYoutube_url()); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
//        if (errorReason.isUserRecoverableError()) {
//            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
//        } else {
//            String error = String.format(errorReason.toString(), String.valueOf(R.string.player_error));
//            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RECOVERY_REQUEST) {
//            // Retry initialization if user performed a recovery action
//            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
//        }
//    }
//
//    protected Provider getYouTubePlayerProvider() {
//        return youTubeView;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void showMessage(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//    }
}


