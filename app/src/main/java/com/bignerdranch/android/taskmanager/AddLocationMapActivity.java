package com.bignerdranch.android.taskmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class AddLocationMapActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String ADDRESS_RESULT = "address";

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private Button mapLocationButton;
    private Button useLocationButton;
    private EditText addressText;
    private Address address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_maps);

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
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();

        // Add zoom buttons
        mUiSettings.setZoomControlsEnabled(true);
        // Add my location button
        mUiSettings.setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        setUpViews();
        setUpViews();
    }

    private void setUpViews() {
        addressText = (EditText) findViewById(R.id.task_address);
        mapLocationButton = (Button) findViewById(R.id.map_location_button);
        useLocationButton = (Button) findViewById(R.id.use_this_location_button);
        useLocationButton.setEnabled(false);

        useLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (address != null) {
                    Intent intent = new Intent();
                    intent.putExtra(ADDRESS_RESULT, address);
                    setResult(RESULT_OK, intent);
                }

                finish();
            }
        });
        mapLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mapCurrentAddress();
            }
        });
    }

    protected void mapCurrentAddress() {
        String location = addressText.getText().toString();
        List<Address> addresses;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addresses = geocoder.getFromLocationName(location, 1);
                if (addresses.size() > 0) {
                    address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
                    mMap.moveCamera(center);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                    mMap.animateCamera(zoom);
                    useLocationButton.setEnabled(true);
                } else {
                    // show the user a note that we failed to get an address
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
