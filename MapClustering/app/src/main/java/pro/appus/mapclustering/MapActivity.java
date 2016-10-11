package pro.appus.mapclustering;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by x_prt on 9/26/16
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<XItem> receivedMarkerList = new ArrayList<>();
    private ArrayList<XItem> bitmappedItemList = new ArrayList<>();
    private ClusterManager<XItem> clusterManager;
    private HashMap<LatLng, Bitmap> iconHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fillMarkerCollection();

        new FillClustersTask(receivedMarkerList).execute();
    }

    private void fillMarkerCollection() {
        //mock data from server
        receivedMarkerList.add(new XItem("Appus Studio", new LatLng(50.003955, 36.241108), "https://media.licdn.com/mpr/mpr/shrink_200_200/AAEAAQAAAAAAAAenAAAAJGYzY2FlNDc0LWI4NTYtNDM0Yi1iZGFlLWMwN2MwZTA0YWFlYg.png"));
        receivedMarkerList.add(new XItem("Farsh", new LatLng(50.005096, 36.244663), "https://pp.vk.me/c625619/v625619013/3d381/Pk6GnzDjm-4.jpg"));
        receivedMarkerList.add(new XItem("Freshline", new LatLng(50.005767, 36.235539), "http://qimpo.com/HomeAuxiliary/GetShowCompanyImage?imageCode=28416_0&screenWidth=1024"));
        receivedMarkerList.add(new XItem("KhNUCA", new LatLng(50.000371, 36.234900), "http://kstuca.kharkov.ua/uploads/khnuba_logo.png"));
        receivedMarkerList.add(new XItem("McDonald's", new LatLng(49.997788, 36.239492), "http://sj23.blox.pl/resource/download_6.jpg"));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.003955, 36.241108), 13f));

        clusterManager = new ClusterManager<>(this, mMap);
        clusterManager.setRenderer(new XMarkerRenderer(this, mMap, clusterManager));
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                clusterManager.onCameraChange(mMap.getCameraPosition());
            }
        });
        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(clusterManager.getMarkerManager());

        XClusterInfoAdapter xClusterInfoAdapter = new XClusterInfoAdapter(this);
        XMarkerInfoAdapter xMarkerInfoAdapter = new XMarkerInfoAdapter(this, iconHashMap);

        clusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(xClusterInfoAdapter);
        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(xMarkerInfoAdapter);

        clusterManager.addItems(bitmappedItemList);
        clusterManager.cluster();
    }

    class FillClustersTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<XItem> markerItemList;
        ArrayList<XItem> tempItemList = new ArrayList<>();

        public FillClustersTask(ArrayList<XItem> markerItemList) {
            this.markerItemList = markerItemList;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for (XItem item : markerItemList) {
                Bitmap itemBitmap = null;
                try {
                    itemBitmap = Picasso.with(MapActivity.this).load(item.getIconUrl()).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tempItemList.add(new XItem(item.getName(), item.getLocation(), itemBitmap));
                iconHashMap.put(item.getLocation(), itemBitmap);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            bitmappedItemList = tempItemList;
            initMap();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("x_prt", "onMarkerClick");
        return false;
    }
}
