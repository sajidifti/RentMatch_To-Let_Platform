package edu.ewubd.loginandsignup;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
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
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowLocation extends AppCompatActivity {

    private MapView mapView;

    boolean directionBtnClicked = false;

    FloatingActionButton fabGoBack, fabDirection;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    MapboxMap mapboxMap;
    MapboxMap map;
    Point origin;

    double latitudeFinal = 23.7675505;
    double longitudeFinal = 90.4281364;

    double latitudeFinalSecond = 23.7675505;
    double longitudeFinalSecond = 90.4281364;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i3 = getIntent();

        latitudeFinalSecond = i3.getDoubleExtra("latPicked", 0.0);
        longitudeFinalSecond = i3.getDoubleExtra("lngPicked", 0.0);

        Mapbox.getInstance(this, getResources().getString(R.string.accessToken));

        setContentView(R.layout.activity_show_location);

        mapView = findViewById(R.id.mapView);

        fabGoBack = findViewById(R.id.fabGoBack);
        fabDirection = findViewById(R.id.fabDirection);

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

        origin = Point.fromLngLat(longitudeFinal, latitudeFinal);

        loadMap();

        fabGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        fabDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked on Direction!");
                directionBtnClicked = true;
                loadMap();
            }
        });

    }

    //    Map
    private void loadMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
//                        Current Location
                        style.addImage("red-pin-icon-id", BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.baseline_place_24)));

                        style.addLayer(new SymbolLayer("icon-layer-id", "icon-source-id").withProperties(
                                iconImage("red-pin-icon-id"),
                                iconIgnorePlacement(true),
                                iconAllowOverlap(true),
                                iconOffset(new Float[]{0f, -9f})
                        ));

                        GeoJsonSource iconGeoJsonSource = new GeoJsonSource("icon-source-id", Feature.fromGeometry(Point.fromLngLat(longitudeFinal, latitudeFinal)));
                        style.addSource(iconGeoJsonSource);
                        mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeFinal, longitudeFinal), 15.78));

                        System.out.println("Latitude: " + latitudeFinal);
                        System.out.println("Longitude: " + longitudeFinal);

                        //Second Point
                        if (latitudeFinalSecond != 0.0 && longitudeFinalSecond != 0.0) {
                            style.addImage("red-pin-icon-id-2", BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.baseline_place_48)));

                            style.addLayer(new SymbolLayer("icon-layer-id-2", "icon-source-id-2").withProperties(
                                    iconImage("red-pin-icon-id-2"),
                                    iconIgnorePlacement(true),
                                    iconAllowOverlap(true),
                                    iconOffset(new Float[]{0f, -9f})
                            ));

                            GeoJsonSource iconGeoJsonSource2 = new GeoJsonSource("icon-source-id-2", Feature.fromGeometry(Point.fromLngLat(longitudeFinalSecond, latitudeFinalSecond)));
                            style.addSource(iconGeoJsonSource2);
                            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeFinalSecond, longitudeFinalSecond), 15.78));

                            //For Direction
                            if (directionBtnClicked) {
                                style.addSource(new GeoJsonSource("route-source-id"));

                                LineLayer routeLayer = new LineLayer("route-layer-id", "route-source-id");
                                routeLayer.setProperties(
                                        lineCap(Property.LINE_CAP_ROUND),
                                        lineJoin(Property.LINE_JOIN_ROUND),
                                        lineWidth(5f),
                                        lineColor(Color.parseColor("#006eff"))
                                );

                                style.addLayer(routeLayer);

                                Point destination = Point.fromLngLat(longitudeFinalSecond, latitudeFinalSecond);
                                getRoute(mapboxMap, origin, destination);
                            }
                        }
                    }
                });
            }
        });
    }

    //For Direction
    private void getRoute(MapboxMap mapboxMap, Point Origin, Point destination) {
        MapboxDirections client = MapboxDirections.builder().origin(origin).destination(destination).overview(DirectionsCriteria.OVERVIEW_FULL).profile(DirectionsCriteria.PROFILE_DRIVING).accessToken(getString(R.string.accessToken)).build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.body() == null) {
                    Log.e("Failure", "No routes found. Set token");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Log.e("Failure", "No routes found");
                }

                DirectionsRoute drivingRoute = response.body().routes().get(0);

                if (mapboxMap != null) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            GeoJsonSource routeLineSource = style.getSourceAs("route-source-id");
                            GeoJsonSource iconGeoJsonSource = style.getSourceAs("icon-source-id-2");

                            if (routeLineSource != null) {
                                routeLineSource.setGeoJson(LineString.fromPolyline(drivingRoute.geometry(), PRECISION_6));
                                if (iconGeoJsonSource == null) {
                                    iconGeoJsonSource = new GeoJsonSource("icon-source-id-2", Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude())));
                                    style.addSource(iconGeoJsonSource);
                                } else {
                                    iconGeoJsonSource.setGeoJson(Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude())));
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
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
        finish();
    }

}