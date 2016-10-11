package pro.appus.mapclustering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by x_prt on 9/28/16
 */
class XClusterInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    XClusterInfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return LayoutInflater.from(context).inflate(R.layout.cluster_info, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
