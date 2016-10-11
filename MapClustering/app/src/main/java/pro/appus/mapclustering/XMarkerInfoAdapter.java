package pro.appus.mapclustering;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by x_prt on 9/28/16
 */
public class XMarkerInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private HashMap<LatLng, Bitmap> iconMap = new HashMap<>();

    public XMarkerInfoAdapter(Context context, HashMap<LatLng, Bitmap> iconMap) {
        this.context = context;
        this.iconMap = iconMap;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.marker_info, null);
        TextView markerInfo = (TextView) view.findViewById(R.id.tv_maker_info_text);
        markerInfo.setText(marker.getTitle());
        ImageView ivMarkerIcon = (ImageView) view.findViewById(R.id.iv_info_icon);
        ivMarkerIcon.setImageBitmap(iconMap.get(marker.getPosition()));
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
