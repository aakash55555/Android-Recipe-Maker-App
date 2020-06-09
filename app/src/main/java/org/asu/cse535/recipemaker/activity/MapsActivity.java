package org.asu.cse535.recipemaker.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.asu.cse535.recipemaker.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Double userLat;
    private Double userLon;

    private Double restaurantLat;
    private Double restaurantLon;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        userLat = intent.getDoubleExtra("userLat",0);
        userLon = intent.getDoubleExtra("userLon",0);
        restaurantLat = intent.getDoubleExtra("restaurantLat",0);
        restaurantLon = intent.getDoubleExtra("restaurantLon",0);
        title = intent.getStringExtra("title");
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
        mMap = googleMap;
        mMap.clear();

        LatLng restaurantLocation = new LatLng(restaurantLat, restaurantLon);
        MarkerOptions restaurantMarker = new MarkerOptions().position(restaurantLocation).title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        restaurantMarker.anchor(.5f,.5f);
        mMap.addMarker(restaurantMarker);

        LatLng userLocation = new LatLng(userLat, userLon);
        MarkerOptions userMarker = new MarkerOptions().position(userLocation).title("Your Location");
        userMarker.anchor(.5f,.5f);
        mMap.addMarker(userMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) );
    }
}
