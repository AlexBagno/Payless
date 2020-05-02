package com.example.payless;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ImageButton cup;
    ImageButton hanger;
    ImageButton wifi;
    ArrayList<Place> places ;
    String[] placeCoors;
    String neededTag;
    private static final  int ERROR_DIALOG_REQUEST = 9001;

    private static final String TAG =  "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_act);
        configureCupButton();
        configureHangerButton();
        configureWIFIButton();

        places = new ArrayList<Place>();
        if (isServiceOK()){
            init();
        }



    }
    private void init(){
        hanger.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SearchActivity.this, MapsShop.class);
                startActivity(intent);
            }
        });
    }



    public boolean isServiceOK() {
        Log.d(TAG, "isServiceOK : checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SearchActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;


        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOk: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(SearchActivity.this, available, ERROR_DIALOG_REQUEST);
        }else {
            Toast.makeText(this, "You cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }




    private void configureCupButton(){
        cup = findViewById(R.id.cup_search);

        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neededTag = "cafe";
                fillCoors(neededTag);

                Intent intent =new Intent(SearchActivity.this,MapsShop.class);
                intent.putExtra("coors",placeCoors);
                startActivity(intent);
            }
        });
    }

    private void configureHangerButton(){
        hanger = findViewById(R.id.hanger_search);

        hanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neededTag = "Clothes";
                fillCoors(neededTag);

               /* Intent intent =new Intent(SearchActivity.this,MapsShop.class);
                intent.putExtra("coors",placeCoors);
                startActivity(intent);*/
            }
        });
    }

    private void configureWIFIButton(){
        wifi = findViewById(R.id.wifi_search);

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neededTag = "hotspot";
                fillCoors(neededTag);

                Intent intent =new Intent(SearchActivity.this,MapsShop.class);
                intent.putExtra("coors",placeCoors);
                startActivity(intent);
            }
        });
    }
    private void fillPlaces(){

    }

    private void fillCoors(String tag){
        for (Integer i = 0; i < places.size(); i++) {
            if(places.get(i).tag==tag){
              //  placeCoors[i] =places.get(i).coor;

            }



        }
    }


}
