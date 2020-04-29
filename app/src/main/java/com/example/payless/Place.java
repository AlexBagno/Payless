package com.example.payless;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Place {
    LatLng coor;
    String name;
    String tag;
    MarkerOptions marker;

    Place(LatLng coors, String name, String tag){
        coor = coors;
        this.name = name;
        this.tag=tag;
        marker= new MarkerOptions().position(coors).title(name).flat(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
    }

}
