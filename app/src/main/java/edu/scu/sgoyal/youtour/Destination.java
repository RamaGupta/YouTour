package edu.scu.sgoyal.youtour;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by raggupta on 2016-05-23.
 */
public class Destination
{

    public String name;
    public String youtube_url;
    public String description;
    public String image;
//    public float
    public LatLng latLng;
    public double lat;
    public double lng;

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public Destination(String name, String youtube_url, String description, String image, double lat, double lng)
    {
        this.name = name;
        this.youtube_url = youtube_url;
        this.description = description;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
        this.latLng = new LatLng(lat, lng);
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng()
    {
        return latLng;
    }

    public void setLatLng(LatLng latLng)
    {
        this.latLng = latLng;
    }

    public void setName(String name) {
        this.name = name;
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
}
