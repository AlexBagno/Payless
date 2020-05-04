package com.example.payless;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsShop extends FragmentActivity implements OnMapReadyCallback  {



    private static final String TAG =  "MapsShop";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 15;
    private  FusedLocationProviderClient mFusedLocationProviderClient;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final  int REQUEST_CODE = 101;
    private EditText mSearchText;
    private ImageView mGps;
    int PROXIMITY_RADIUS =50000;
    double latitude, longitude;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_shop);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mSearchText = (EditText) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        getLocationPermission();




    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the device current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");

                        }else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsShop.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }





    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsShop.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocation|Permission: getting location permission");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }else {
                ActivityCompat.requestPermissions(this,
                        permission,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,
                    permission,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }







    private void init(){
        Log.d(TAG, "init : initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    geoLocate();

                }


                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        hideSoftKeyboard();

    }


    public void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(MapsShop.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }
        if (list.size() > 0){
            mMap.clear();
        Address address = list.get(0);
        Log.d(TAG, "geoLocate : found a location: " + address.toString());
        LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
    }}






    /**
     *  Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
   public void onMapReady(GoogleMap googleMap) {
      /*  LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your location").flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(markerOptions);*/
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap=googleMap;
        if (mLocationPermissionGranted){
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();


        }

        googleMap.setInfoWindowAdapter(new CustominfoWindowAdapter(MapsShop.this));

    }

    public void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

       if (!title.equals("My Location")){MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title)
                .flat(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mMap.addMarker(options);
       }
        hideSoftKeyboard();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionResult: called.");
        mLocationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0 ){
                    for (int i = 0; i< grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionResult: permission granted");
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }

    /*@Override
    public boolean onMarkerClick(Marker marker) {
        return;
    }*/



    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }







    public void onClick (View view){
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        String url;





        switch (view.getId()){
            case R.id.cafe:
                try {
                    if (mLocationPermissionGranted){
                        final Task location = mFusedLocationProviderClient.getLastLocation();
                        location.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "onComplete: found location!");
                                    Location currentLocation = (Location) task.getResult();
                                    mMap.clear();
                                    String cafe = "cafe";
                                    String url = getUrl(currentLocation.getLatitude(),currentLocation.getLongitude(), cafe);
                                    Object dataTransfer[] = new Object[2];
                                    dataTransfer[0] = mMap;
                                    dataTransfer[1] = url;
                                    GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                                    getNearbyPlacesData.execute(dataTransfer);
                                    Toast.makeText(MapsShop.this, "Showing Nearby Cafe", Toast.LENGTH_SHORT).show();


                                }else {
                                    Log.d(TAG, "onComplete: current location is null");
                                    Toast.makeText(MapsShop.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }catch (SecurityException e){
                    Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
                }

                break;


            case R.id.store:
                try {
                    if (mLocationPermissionGranted){
                        final Task location = mFusedLocationProviderClient.getLastLocation();
                        location.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "onComplete: found location!");
                                    Location currentLocation = (Location) task.getResult();
                                    mMap.clear();
                                    String shopping_mall = "shopping_mall";
                                    String url = getUrl(currentLocation.getLatitude(),currentLocation.getLongitude(), shopping_mall);
                                    Object dataTransfer[] = new Object[2];
                                    dataTransfer[0] = mMap;
                                    dataTransfer[1] = url;
                                    GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                                    getNearbyPlacesData.execute(dataTransfer);
                                    Toast.makeText(MapsShop.this, "Showing Nearby Store", Toast.LENGTH_SHORT).show();



                                }else {
                                    Log.d(TAG, "onComplete: current location is null");
                                    Toast.makeText(MapsShop.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }catch (SecurityException e){
                    Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
                }



                break;
            case R.id.resta:
                try {
                    if (mLocationPermissionGranted){
                        final Task location = mFusedLocationProviderClient.getLastLocation();
                        location.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "onComplete: found location!");
                                    Location currentLocation = (Location) task.getResult();
                                    mMap.clear();
                                    String restaurant = "restaurant";
                                    String url = getUrl(currentLocation.getLatitude(),currentLocation.getLongitude(), restaurant);
                                    Object dataTransfer[] = new Object[2];
                                    dataTransfer[0] = mMap;
                                    dataTransfer[1] = url;
                                    GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                                    getNearbyPlacesData.execute(dataTransfer);
                                    Toast.makeText(MapsShop.this, "Showing Nearby Restaurant", Toast.LENGTH_SHORT).show();



                                }else {
                                    Log.d(TAG, "onComplete: current location is null");
                                    Toast.makeText(MapsShop.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }catch (SecurityException e){
                    Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
                }
                break;
            case R.id.clothing_store:
                try {
                    if (mLocationPermissionGranted){
                        final Task location = mFusedLocationProviderClient.getLastLocation();
                        location.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "onComplete: found location!");
                                    Location currentLocation = (Location) task.getResult();
                                    mMap.clear();
                                    String clothing_store = "clothing_store";
                                    String url = getUrl(currentLocation.getLatitude(),currentLocation.getLongitude(), clothing_store);
                                    Object dataTransfer[] = new Object[2];
                                    dataTransfer[0] = mMap;
                                    dataTransfer[1] = url;
                                    GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                                    getNearbyPlacesData.execute(dataTransfer);
                                    Toast.makeText(MapsShop.this, "Showing Nearby Clothing Store", Toast.LENGTH_SHORT).show();



                                }else {
                                    Log.d(TAG, "onComplete: current location is null");
                                    Toast.makeText(MapsShop.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }catch (SecurityException e){
                    Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
                }
                break;

        }
    }


    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {


        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&key=AIzaSyCXvAe5yWBb4Nu1mCrzqKQep0VELdcxHRE");
        googlePlaceUrl.append("&sensor=true");

        Log.d(TAG, "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }




}
class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private GoogleMap mMap;
    String url;
    private static final String TAG =  "MapsShop";


    @Override
    protected String doInBackground(Object... objects){
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){

        List<HashMap<String, String>> nearbyPlaceList;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        Log.d(TAG,"called parse method");
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {
        for(int i = 0; i < nearbyPlaceList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");

            double lat = Double.parseDouble( googlePlace.get("lat"));
            double lng = Double.parseDouble( googlePlace.get("lng"));

            LatLng latLng = new LatLng( lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : "+ vicinity);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}


