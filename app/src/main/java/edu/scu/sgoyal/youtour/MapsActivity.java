package edu.scu.sgoyal.youtour;

import android.*;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    static int currentPosition = 0;
    private GoogleMap mMap;
    LatLng latLngSCU;
    static List<Destination> destinations;
    private BeaconManager beaconManager;
    private static Context appContext;

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }

        destinations = new ArrayList<>();
        destinations.add(new Destination("Church", "ElRLtIus6xk", "Historic Mission Santa Clara is a beautiful icon that sits at the center of our campus.  First established in 1777, the Franciscan Order handed the Mission over to the Society of Jesus (the Jesuits) in 1851, who then started Santa Clara College, the first institution of higher education in California. " +
                "Today, the Mission serves as the student chapel for Santa Clara Universtiy.", "church.jpg", 37.349284, -121.941061));
        destinations.add(new Destination("Library", "ez9Z7rHqk1Y", "The Learning Commons was built to create gathering places to use informational, technological, and media resources for teaching, learning, and scholarship. The full range of technology, coupled with" +
                " knowledgeable human resources, helps visitors find, understand, evaluate, and manipulate information" +
                "to meet their diverse needs.", "library.jpg", 37.348317, -121.938034));
        destinations.add(new Destination("Museum", "fekk_11n4Zw", "The de Saisset Museum houses an extensive collection numbering more than 12,000 objects. The museum's Art collection includes painting and sculpture, works on paper and photography, new media, and decorative arts. The History collection is divided into two main areas: California History and Mission-era liturgical vestments. Since its founding in 1955, the museum has developed large and encyclopedic holdings, covering a wide range of art historical periods and styles. Although the museum is committed to caring for every piece in its collection," +
                "current collecting activity is predominantly focused on works by contemporary California artists.", "museum.jpg", 37.349989, -121.940611));
        destinations.add(new Destination("Solar house", "RZP1ljoQLDA", "Radiant House is a 980 square-foot, net-zero energy home designed and constructed by Santa Clara University students from January 2012 through August, 2013, to compete in the Solar Decathalon.", "solar.jpg", 37.347774, -121.939269));
        destinations.add(new Destination("Malley Center", "pQkyPbUW6Cw", "It is designed to be a gathering place for members of the campus community interested in physical activity. All individuals, regardless of ability level, are encouraged to utilize this campus resource to improve personal health and well-being. Both the building layout and facility amenities are intended to create a welcoming, user-friendly environment.\n" +
                "\n" +
                "The Malley Center has many features to ensure a rewarding recreational experience: air conditioning and heating in all rooms, numerous windows, and a \"clear story\" throughout the building to allow natural light to filter into the facility.", "maley.jpg", 37.348653, -121.936480));

        latLngSCU = new LatLng(37.349523, -121.938729);
        ((Button) findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String destination = destinations.get(currentPosition).getLat()+","+destinations.get(currentPosition).getLng();
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+destination+"+&mode=w");
                System.out.println(gmmIntentUri);
                currentPosition++;
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                finish();
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        beaconManager = new BeaconManager(getApplicationContext());
        // beaconManager.setBackgroundScanPeriod(100,100);
        appContext = getApplicationContext();

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {

            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {

                Intent i = new Intent(appContext, View_destination.class);
//                i.putExtra("POSITION_ID", 1);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                showNotification(
                        "Your gate closes in 47 minutes.",
                        "Current security wait time is 15 minutes, "
                                + "and it's a 5 minute walk from security to the gate. "
                                + "Looks like you've got plenty of time!");
                startActivity(i);

            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        47753, 22035));
            }

        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        MarkerOptions m = new MarkerOptions();
//        Marker x = new Marker(mMap.);
        for (Destination d : destinations)
        {
            mMap.addMarker(new MarkerOptions().position(d.getLatLng()).title(d.getName()).snippet(d.getImage()));


        }
        Toast.makeText(this, "in on map ready", Toast.LENGTH_LONG).show();

        mMap.addMarker(new MarkerOptions().position(latLngSCU).title("Santa Clara University").snippet("Santa Clara"));

         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngSCU, 16.0f));

        UiSettings ui = mMap.getUiSettings();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            ui.setMyLocationButtonEnabled(true);
        } else {
            Toast.makeText(this, "error in map", Toast.LENGTH_LONG).show();
        }
        ui.setAllGesturesEnabled(true);
        ui.setCompassEnabled(true);
        ui.setZoomControlsEnabled(true);
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

}
