package com.bignerdranch.android.taskmanager;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

/**
 * Created by Eduardo on 12/2/2016.
 */
public class AddLocationMapActivity extends MapActivity {

    public static final String ADDRESS_RESULT = "address";

    /*private Button mapLocationButton;
    private Button useLocationButton;
    private EditText addressText;
    private MapView mapView;
    private Address address;*/

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_location);
        //setUpViews();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    protected boolean isLocationDisplayed() {
        return true;
    }

    /*private void setUpViews() {
        addressText = (EditText) findViewById(R.id.task_address);
        mapLocationButton = (Button) findViewById(R.id.map_location_button);
        useLocationButton = (Button) findViewById(R.id.use_this_location_button);
        mapView = (MapView) findViewById(R.id.map);
        useLocationButton.setEnabled(false);
        mapView.setBuiltInZoomControls(true);

        /*mapLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mapCurrentAddress();
            }
        });
    }*/

    /*protected void mapCurrentAddress() {
        String addressString = addressText.getText().toString();
        Geocoder g = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = g.getFromLocationName(addressString, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0);
                List<Overlay> mapOverlays = mapView.getOverlays();
                AddressOverlay addressOverlay = new AddressOverlay(address);
                mapOverlays.add(addressOverlay);
                mapView.invalidate();
                final MapController mapController = mapView.getController();
                mapController.animateTo(addressOverlay.getGeopoint(), new Runnable() {
                    public void run() {
                        mapController.setZoom(12);
                    }
                });
                useLocationButton.setEnabled(true);
            } else {
                // show the user a note that we failed to get an address
            }
        } catch (IOException e) {
            // show the user a note that we failed to get an address
            e.printStackTrace();
        }
    }*/
}
