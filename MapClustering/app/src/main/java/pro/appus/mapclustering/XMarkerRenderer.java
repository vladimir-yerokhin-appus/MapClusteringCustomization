package pro.appus.mapclustering;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by x_prt on 9/29/16
 */

class XMarkerRenderer extends DefaultClusterRenderer<XItem> {

    private final IconGenerator markerIconGenerator;
    private final IconGenerator clusterIconGenerator;
    private final ImageView mImageView;
    private final ImageView mItemImageView;
    private Context context;

    XMarkerRenderer(Context context, GoogleMap map, ClusterManager<XItem> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.multi_profile, null);
        clusterIconGenerator = new IconGenerator(context);
        clusterIconGenerator.setContentView(view);
        markerIconGenerator = new IconGenerator(context);

        int mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
        int padding = (int) context.getResources().getDimension(R.dimen.custom_profile_padding);

        mItemImageView = (ImageView) view.findViewById(R.id.x_image);
        mImageView = new ImageView(context);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        mImageView.setPadding(padding, padding, padding, padding);

        markerIconGenerator.setContentView(mImageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(XItem xItem, MarkerOptions markerOptions) {
        mImageView.setImageBitmap(xItem.getIconBitmap());
        Bitmap icon = markerIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(xItem.getName());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<XItem> cluster, MarkerOptions markerOptions) {
        mItemImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.common_google_signin_btn_icon_dark_normal));
        Bitmap icon = clusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<XItem> cluster) {
        return cluster.getSize() > 1;
    }
}
