package edu.scu.sgoyal.youtour;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.estimote.sdk.Region;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by raggupta on 2016-05-23.
 */
public class Destination implements Parcelable {

    private static ArrayList<Destination> destinations;
    private String name;
    private String youtube_url;
    private String description;
    private String image;
    private LatLng latLng;
    private double lat;
    private double lng;
    private Region region;
    private String regionTitle;
    private String UUID;
    private int becaonMinor;
    private int beaconMajor;
    private ArrayList<Double> ratings;
    private ArrayList<String> comments;
    private double averageRating = 5;

    public double getAverageRating()
    {
        return averageRating;
    }

    public void setAverageRating(double averageRating)
    {
        this.averageRating = averageRating;
    }

    public Destination() {

    }

    public Destination(String name, String youtube_url, String description, String image,
                       double lat, double lng, String regionTitle, String UUID, int becaonMinor,
                       int beaconMajor, ArrayList<Double> ratings, ArrayList<String> comments, Double averageRating) {
        this.name = name;
        this.youtube_url = youtube_url;
        this.description = description;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
        this.regionTitle = regionTitle;
        this.UUID = UUID;
        this.becaonMinor = becaonMinor;
        this.beaconMajor = beaconMajor;
        this.ratings = ratings;
        this.comments = comments;
        this.averageRating = averageRating;
    }



    public Destination(String name, String youtube_url, String description, String image,
                       double lat, double lng, Region region) {
        this.name = name;
        this.youtube_url = youtube_url;
        this.description = description;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
        this.latLng = new LatLng(lat, lng);
        this.region = region;
    }

   protected Destination(Parcel in)
    {
        name = in.readString();
        youtube_url = in.readString();
        description = in.readString();
        image = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        lat = in.readDouble();
        lng = in.readDouble();
        region = in.readParcelable(Region.class.getClassLoader());
    }


    public static final Creator<Destination> CREATOR = new Creator<Destination>() {
        @Override
        public Destination createFromParcel(Parcel in) {
            return new Destination(in);
        }

        @Override
        public Destination[] newArray(int size) {
            return new Destination[size];
        }
    };

    public static ArrayList<Destination> getDestinations() {
        if (destinations == null) {
//            destinations = new ArrayList<Destination>();

            //TODO configure destinations by querying database

//            destinations = Utility.configureDestination();
            destinations = MapsActivity.dbDataDestinatios;

        }
        return destinations;
    }

    public void setDestinations(ArrayList<Destination> destinations) {
        this.destinations = destinations;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getBecaonMinor() {
        return becaonMinor;
    }

    public void setBecaonMinor(int becaonMinor) {
        this.becaonMinor = becaonMinor;
    }

    public int getBeaconMajor() {
        return beaconMajor;
    }

    public void setBeaconMajor(int beaconMajor) {
        this.beaconMajor = beaconMajor;
    }

    public ArrayList<Double> getRatings() {
        if(ratings == null)
        {
            ratings = new ArrayList<Double>();
        }
        return ratings;
    }

    public void setRatings(ArrayList<Double> ratings) {
        this.ratings = ratings;
    }

    public ArrayList<String> getComments() {
        if(comments == null)
        {
            comments = new ArrayList<String>();
        }
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setLatLng() {
        this.latLng = new LatLng(this.getLat(),this.getLng());
    }

    public void setLatLng(double lat, double lng) {
        this.latLng = new LatLng(lat,lng);
    }

    public String getYoutube_url() {
        return youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setRegion(String regionTitle, String UUID, int beaconMajor, int becaonMinor) {
        Log.i("Setting Region" ,"Setting REgiom ==================");
        this.region = new Region(regionTitle, java.util.UUID.fromString(UUID), beaconMajor, becaonMinor);
    }

    public void setRegion() {
        this.region = new Region(this.regionTitle, java.util.UUID.fromString(this.UUID), this.beaconMajor, this.becaonMinor);
    }






    public static Destination getDestinationBasedOnName(String s) {
        for (Destination d : Destination.getDestinations()) {
            if(d.getName().equals(s)){
                return  d;
            }
        }
        return null;
    }
    public static Destination getDestinationBasedOnRegion(Region region) {
        for (Destination d :
                Destination.getDestinations()) {
            if(region == d.getRegion()){
                return d;
            }
        }
        return null;
    }

    public static Region getRegionBasedOnDestination(String s) {
        for (Destination d : Destination.getDestinations()) {
            if(d.getName().equals(s)){
                return  d.getRegion();
            }
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(youtube_url);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeParcelable(latLng, flags);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeParcelable(region, flags);
        dest.writeString(regionTitle);
        dest.writeString(UUID);
        dest.writeLong(becaonMinor);
        dest.writeLong(beaconMajor);
        dest.writeStringList(comments);
    }
}
