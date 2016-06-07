package edu.scu.sgoyal.youtour;

/**
 * Created by raggupta on 2016-05-25.
 */
public class Image {
    public String items;
    public int thumbnails;
    public int largeImages;

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public int getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(int thumbnails) {
        this.thumbnails = thumbnails;
    }

    public int getLargeImages() {
        return largeImages;
    }

    public void setLargeImages(int largeImages) {
        this.largeImages = largeImages;
    }

    public Image(String items, int thumbnails, int largeImages) {
        this.items = items;
        this.thumbnails = thumbnails;
        this.largeImages = largeImages;
    }
}
