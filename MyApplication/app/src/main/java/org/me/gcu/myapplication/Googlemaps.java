// Name: Subhaan Saleem     Matriculation Number: S1708061

package org.me.gcu.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Googlemaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap Maps;
    private ArrayList<PullParser> alist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_googlemaps);
        alist = (ArrayList<PullParser>) getIntent().getExtras().getSerializable("Items");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        Maps = googleMap;
        // Coordinates to default view of map to the UK
        LatLng uk = new LatLng(53.3500, -2.4333);

        for (int i = 0; i < alist.size(); i++) {
            BitmapDescriptor bmd = null;

            if (Float.parseFloat(alist.get(i).getMagnitude()) < 1) {
                bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            } else if (Float.parseFloat(alist.get(i).getMagnitude()) >= 1 && Float.parseFloat(alist.get(i).getMagnitude()) <= 2) {
                bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            } else if (Float.parseFloat(alist.get(i).getMagnitude()) > 2) {
                bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            }
            Maps.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(alist.get(i).getLatitude()), Double.parseDouble(alist.get(i).getLongitude()))).icon(bmd).title(alist.get(i).getLocation()));
        }
        Maps.moveCamera(CameraUpdateFactory.newLatLngZoom(uk, 6));
    }
}