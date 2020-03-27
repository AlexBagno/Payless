package com.example.payless;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {
    ImageButton back;
    private GoogleMap map;
    SupportMapFragment mapFragment;

    private List<Place> foundPlaces ;

    private Spinner places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        onMapReady(map);
        createMapView();
        addMarker();
        configureBackButton();
        foundPlaces = new ArrayList<Place>();


    }



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
        this.map = googleMap;

    }



    void addMarker(){
        if(null != map){
            LatLng dnepr = new LatLng(48.45, 34.98);
            map.addMarker(new MarkerOptions().position(dnepr).title("Your town")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(dnepr,10));
        }
    }






    private void createMapView(){
        try{
            if(map == null){
                ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
                if (map == null){
                    Toast.makeText(getApplicationContext(), "Error creating map", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (NullPointerException e) {
            Log.e("mapApp",e.toString());
        }
    }


    private void configureBackButton(){
        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); {

        }

    }

}




