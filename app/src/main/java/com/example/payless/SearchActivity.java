package com.example.payless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ImageButton cup;
    ImageButton hanger;
    ImageButton wifi;
    ArrayList<Place> places ;
    String[] placeCoors;
    String neededTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_act);
        configureCupButton();
        configureHangerButton();
        configureWIFIButton();

        places = new ArrayList<Place>();


    }
    private void configureCupButton(){
        cup = findViewById(R.id.cup_search);

        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neededTag = "cafe";
                fillCoors(neededTag);

                Intent intent =new Intent(SearchActivity.this,MapsActivity2.class);
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

                Intent intent =new Intent(SearchActivity.this,MapsShop.class);
                intent.putExtra("coors",placeCoors);
                startActivity(intent);
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

                Intent intent =new Intent(SearchActivity.this,Maps3.class);
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
                placeCoors[i] =places.get(i).coor;

            }



        }
    }


}
