package pro.appus.mapclustering;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by x_prt on 9/26/16
 */

public class XItem implements ClusterItem {

    private String name;
    private LatLng location;
    private String iconUrl;
    private Bitmap iconBitmap;

    private int iconRes;

//    public XItem(String name, LatLng location, int picture) {
//        this.name = name;
//        this.iconRes = picture;
//        this.location = location;
//    }

    public XItem(String name, LatLng location, String iconUrl) {
        this.name = name;
        this.location = location;
        this.iconUrl = iconUrl;
    }

    public XItem(String name, LatLng location, Bitmap bitmap) {
        this.name = name;
        this.location = location;
        this.iconBitmap = bitmap;
    }

    @Override
    public LatLng getPosition() {
        return getLocation();
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }


    public String getIconUrl() {
        return iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public int getIconRes() {
        return iconRes;
    }
}
