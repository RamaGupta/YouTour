package edu.scu.sgoyal.youtour;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubhamgoyal on 5/24/16.
 */

public class MapsActivity extends MenuActivity implements OnMapReadyCallback
{

    public static Boolean NAVIGATION_STATUS = Boolean.FALSE;
    public static ArrayList<Destination> dbDataDestinatios = new ArrayList<>();
    static int currentPosition = 0;
    private static BeaconManager beaconManager;
    private static Context appContext;
    private static Intent mapIntent;
    IGoogleMapDelegate iGoogleMapDelegate;
    LatLng latLngSCU;
    static Firebase myFirebaseRef;
    private GoogleMap mMap;

    private static BeaconManager getBeaconManager()
    {
        if (beaconManager == null)
        {
            beaconManager = new BeaconManager(appContext);
        }
        return beaconManager;
    }

    public static void showNotification(String title, String message)
    {
        Intent notifyIntent = new Intent(MapsActivity.appContext, ViewDestinationDetailActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Destination d = Destination.getDestinationBasedOnName(title);
        if (d != null)
        {
            notifyIntent.putExtra("DESTINATION", d.getName());
            PendingIntent pendingIntent = PendingIntent.getActivities(MapsActivity.appContext, 0,
                    new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new Notification.Builder(MapsActivity.appContext)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
            notification.defaults |= Notification.DEFAULT_SOUND;
            NotificationManager notificationManager =
                    (NotificationManager) MapsActivity.appContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        } else
        {
            return;
        }


    }

    public static void stopNavigation()
    {
        ActivityManager am = (ActivityManager) appContext.getSystemService(ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo pid : am.getRunningAppProcesses())
        {
            if (pid.processName.equals("com.google.android.apps.maps"))
            {
                am.killBackgroundProcesses("com.google.android.apps.maps");
            }
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

//        getDataFromFireBase(this);
//        configureDestinaotions(dbDataDestinatios);
        appContext = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        BeaconManager bm = MapsActivity.getBeaconManager();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }

        Log.i("MapsActivity", "OnCreate");
        Log.i("MapsActivity", currentPosition + "");
        // TODO dont hard code
        latLngSCU = new LatLng(37.349523, -121.938729);
        Button b = ((Button) findViewById(R.id.startTourButton));
        if (currentPosition != 0)
        {
            b.setText("Continue Tour");
            Log.i("MapsActivity", "Set text");


        }
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                stopAllBeaconMonitoring();
                Destination d = Destination.getDestinations().get(currentPosition);
                if (currentPosition == Destination.getDestinations().size() - 1)
                {
                    currentPosition = 0;
                } else
                {
                    currentPosition++;
                    Log.i("MapsActivity", "Position Increase");

                }
                if (d != null)
                {
                    Region r = d.getRegion();
//                            getRegionBasedOnDestination(marker.getTitle().toString());
                    if (r != null)
                    {
                        setBeaconMonitoring(r);
                        startNavigation(d);
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "Info clicked = " + d.getName().toString() + " does not have a beacon associated with it", Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    Toast.makeText(getApplicationContext(), "Info clicked = " + d.getName().toString() + " is not a valid destination", Toast.LENGTH_SHORT).show();
                }


//                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + d.getLatLng() + "+&mode=w");
//                Log.i("sgoyal", gmmIntentUri.toString());
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.putExtra("DESTINATION", d.getName());
//                currentPosition++;
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
//                finish();
//                Intent intent = new Intent(MapsActivity.this, DestinationListActivity.class);
//                startActivity(intent);

            }
        });


    }

    private void configureDestinaotions(ArrayList<Destination> dbDataDestinations)
    {
        for (Destination d : dbDataDestinations)
        {
            Log.i("Configure Destionation", d.getRegionTitle() + d.getUUID() + d.getBeaconMajor() +
                    d.getBecaonMinor() + d.getLat() + d.getLng());
            d.setRegion(d.getRegionTitle(), d.getUUID(), d.getBeaconMajor(), d.getBecaonMinor());
            d.setLatLng(d.getLat(), d.getLng());
        }
    }

    public static void getDataFromFireBase(Context context)
    {


        Firebase.setAndroidContext(context.getApplicationContext());
        myFirebaseRef = new Firebase("https://testobjects2.firebaseio.com/");

        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
//                dbDataDestinatios = dataSnapshot.getValue();
                Log.i("myFireBase", "children count==============" + dataSnapshot.getChildrenCount());
                for (DataSnapshot dss : dataSnapshot.getChildren())
                {
                    Log.i("myFireBase", "dss==============" + dss.getValue().toString());
                    Destination d = dss.getValue(Destination.class);
                    Log.i("myFireBase", d.getDescription());
                    dbDataDestinatios.add(d);
                    d.setRegion();
                    d.setLatLng();

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });

        // TODO initialize all the pre-requisite needed

    }

    private void stopAllBeaconMonitoring()
    {
        BeaconManager bm = MapsActivity.getBeaconManager();
        for (Destination d : Destination.getDestinations())
        {
            if (d.getRegion() != null)
            {
                bm.stopMonitoring(d.getRegion());

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
//        mMap.setIndoorEnabled(true);

        List<MarkerOptions> markerOptionsList = new ArrayList<MarkerOptions>();
        List<Marker> markers = new ArrayList<Marker>();

        Log.i("MapsActivity", "OnMapReady");

        MarkerOptions mo;

        for (Destination d : Destination.getDestinations())
        {
            mo = new MarkerOptions().position(d.getLatLng()).title(d.getName())
                    .snippet("Click Here To Start Navigation");
            Log.i(this.getClass().getName(), "Destination = " + d.toString());
            if (Utility.getTourStatus().get(Destination.getDestinationBasedOnName(d.getName())))
            {
                Log.i("MapsActivity", "True for visited");
                mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }

            Marker m = mMap.addMarker(mo);
            markerOptionsList.add(mo);
            markers.add(m);


        }
//        Toast.makeText(this, "in on map ready", Toast.LENGTH_LONG).show();

        //mMap.addMarker(new MarkerOptions().position(latLngSCU).title("Santa Clara University").snippet("Santa Clara"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngSCU, 16.0f));
//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                View v = new View(getApplicationContext());
//                ArrayList<View> views = new ArrayList<View>();
//                TextView tv = new TextView(getApplicationContext());
//                tv.setText("Hello");
//                views.add((View)tv);
//                v.addChildrenForAccessibility(views);
//                return v;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                return null;
//            }
//        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
//                Toast.makeText(getApplicationContext(),"Marker clicked = "+marker.getTitle().toString(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                stopAllBeaconMonitoring();
                Toast.makeText(getApplicationContext(), "Info clicked = " + marker.getTitle().toString(), Toast.LENGTH_SHORT).show();
                Destination d = Destination.getDestinationBasedOnName(marker.getTitle().toString());

                if (d != null)
                {
                    Region r = d.getRegion();
                    Log.i("Region", r.toString());
//                            getRegionBasedOnDestination(marker.getTitle().toString());
                    if (r != null)
                    {
                        setBeaconMonitoring(r);
                        startNavigation(d);
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "Region is null = " + marker.getTitle().toString() + " does not a beacon associated with it", Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    Toast.makeText(getApplicationContext(), "Info clicked = " + marker.getTitle().toString() + " is not a valid destination", Toast.LENGTH_SHORT).show();
                }

            }
        });

        UiSettings ui = mMap.getUiSettings();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)
        {
            mMap.setMyLocationEnabled(true);
            ui.setMyLocationButtonEnabled(true);
        } else
        {
            Toast.makeText(this, "error in map", Toast.LENGTH_LONG).show();
        }
//
        ui.setAllGesturesEnabled(true);
        ui.setCompassEnabled(true);
        ui.setZoomControlsEnabled(true);

//        Projection projection = mMap.getProjection();
//        markers.get(0).setInfoWindowAnchor();


    }

    private void startNavigation(Destination d)
    {

        String latLng = d.getLat() + "," + d.getLng();
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latLng + "+&mode=w");
//        System.out.println(gmmIntentUri);
//        currentPosition++;
        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        mapIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mapIntent.putExtra("DESTINATION", d);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivityForResult(mapIntent, 100);
    }

    private void setBeaconMonitoring(final Region region)
    {


        beaconManager.connect(new BeaconManager.ServiceReadyCallback()
        {
            @Override
            public void onServiceReady()
            {
//                beaconManager.startMonitoring(new Region(
//                        "monitored region",
//                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
//                        47753, 22035));
                beaconManager.startMonitoring(region);
            }


        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener()
        {

            @Override
            public void onEnteredRegion(Region region, List<Beacon> list)
            {

                for (Beacon b : list)
                {
                    Log.i("List of beacons sensed ", b.getMajor() + "-" + b.getMinor() + "-" + b.getProximityUUID());
                }

                Intent i = new Intent(appContext, ViewDestinationDetailActivity.class);
                Destination d = Destination.getDestinationBasedOnRegion(region);
                i.putExtra("DESTINATION", d.getName());
                Utility.getTourStatus().put(d, true);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showNotification("Reached " + d.getName(), " Welocome to " + d.getName());

                stopNavigation();
                stopAllBeaconMonitoring();
                startActivity(i);

            }

            @Override
            public void onExitedRegion(Region region)
            {
                // could add an "exit" notification too if you want (-:
                Log.i("Beacons", "In Exit Region");
            }
        });
    }


}
