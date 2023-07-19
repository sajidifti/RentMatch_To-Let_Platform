package edu.ewubd.loginandsignup;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

public class PickLocation extends AppCompatActivity {

    FloatingActionButton fabPickLocation;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    MapboxMap mapboxMap;
    MapboxMap map;
    Point origin;

    double latitudeFinal = 23.7675505;
    double longitudeFinal = 90.4281364;

    double latitudeFinalSecond = 0.0;
    double longitudeFinalSecond = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getResources().getString(R.string.accessToken));

        setContentView(R.layout.activity_pick_location);

        fabPickLocation = findViewById(R.id.fabPickLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    // Handle the current location here
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    latitudeFinal = latitude;
                    longitudeFinal = longitude;
                    // Use the latitude and longitude as needed
                }
            }
        };

        //Put Current Coordinates
        SharedPreferences preferences = PreferenceHelper.getSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        float lng = (float) longitudeFinal;
        float lat = (float) latitudeFinal;
        editor.putFloat("PickedLocationLatitude", lat);
        editor.putFloat("PickedLocationLongitude", lng);
        editor.apply();

        MapView mapView = findViewById(R.id.mapView);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        style.addImage("red-pin-icon-id", BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.baseline_place_24)));

                        style.addLayer(new SymbolLayer("icon-layer-id", "icon-source-id").withProperties(iconImage("red-pin-icon-id"), iconIgnorePlacement(true), iconAllowOverlap(true), iconOffset(new Float[]{0f, -9f})));

                        GeoJsonSource iconGeoJsonSource = new GeoJsonSource("icon-source-id", Feature.fromGeometry(Point.fromLngLat(longitudeFinal, latitudeFinal)));
                        style.addSource(iconGeoJsonSource);
                        mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeFinal, longitudeFinal), 15.78));

                        System.out.println("Latitude: " + latitudeFinal);
                        System.out.println("Longitude: " + longitudeFinal);

                        System.out.println("");

                        map = mapboxMap;
                        map.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                            private boolean isSecondLayerAdded = false;

                            @Override
                            public boolean onMapClick(@NonNull LatLng point) {
                                Point destination = Point.fromLngLat(point.getLongitude(), point.getLatitude());

                                latitudeFinalSecond = destination.latitude();
                                longitudeFinalSecond = destination.longitude();

                                System.out.println("Second Latitude: " + latitudeFinalSecond);
                                System.out.println("Second Longitude: " + longitudeFinalSecond);

                                //Put Picked Coordinates
                                SharedPreferences preferences = PreferenceHelper.getSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                float lng2 = (float) longitudeFinalSecond;
                                float lat2 = (float) latitudeFinalSecond;
                                editor.putFloat("PickedLocationLatitude", lat2);
                                editor.putFloat("PickedLocationLongitude", lng2);
                                editor.apply();

                                System.out.println("Latitude Float: " + lat2);
                                System.out.println("Longitude Float: " + lng2);

                                if (isSecondLayerAdded) {
                                    style.removeLayer("icon-layer-id-2");
                                    style.removeSource("icon-source-id-2");
                                    isSecondLayerAdded = false;
                                }

                                style.addImage("red-pin-icon-id-2", BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.baseline_place_48)));

                                style.addLayer(new SymbolLayer("icon-layer-id-2", "icon-source-id-2").withProperties(iconImage("red-pin-icon-id-2"), iconIgnorePlacement(true), iconAllowOverlap(true), iconOffset(new Float[]{0f, -9f})));

                                GeoJsonSource iconGeoJsonSource2 = new GeoJsonSource("icon-source-id-2", Feature.fromGeometry(Point.fromLngLat(longitudeFinalSecond, latitudeFinalSecond)));
                                style.addSource(iconGeoJsonSource2);
                                mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeFinalSecond, longitudeFinalSecond), 15.78));

                                isSecondLayerAdded = true;

                                return true;
                            }
                        });
                    }
                });
            }
        });

        fabPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Location Picked!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Please Enable Permission for This App in\nSettings>Apps>MapBox>App Info>Permissions>Location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000); // Update location every 5 seconds

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Location Picked!", Toast.LENGTH_SHORT).show();
        finish();
    }
}