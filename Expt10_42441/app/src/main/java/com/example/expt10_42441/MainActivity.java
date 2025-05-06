package com.example.expt10_42441;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Button btnTrackLocation;
    private boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Location Services
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        // Track Location Button
        btnTrackLocation = findViewById(R.id.btnTrackLocation);
        btnTrackLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLocationTracking();
            }
        });

        createLocationRequest();
        createLocationCallback();
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create()
                .setInterval(100000)
                .setFastestInterval(50000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    updateMapLocation(location);
                }
            }
        };
    }

    private void toggleLocationTracking() {
        if (checkLocationPermission()) {
            if (!isTracking) {
                startLocationUpdates();
                btnTrackLocation.setText("Stop Tracking");
                isTracking = true;
            } else {
                stopLocationUpdates();
                btnTrackLocation.setText("Track Location");
                isTracking = false;
            }
        }
    }

    private void startLocationUpdates() {
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (checkLocationPermission()) {
            mMap.setMyLocationEnabled(true);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void updateMapLocation(Location location) {
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.clear();  // Clear previous markers
        mMap.addMarker(new MarkerOptions()
                .position(currentLocation)
                .title("Your Current Location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

        Toast.makeText(this,
                "Location Updated: " + location.getLatitude() + ", "
                        + location.getLongitude(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (checkLocationPermission()) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this,
                        "Location permission denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}