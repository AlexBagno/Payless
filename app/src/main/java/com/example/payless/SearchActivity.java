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
    ArrayList<Place> placeCoors;
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

        places.set(1, new Place("48.4720435,34.9996899","Испанское каппучино","cafe"));
        places.set(2, new Place("48.449755,35.0430067","Шелк от Ульяны","Clothes"));
        places.set(3, new Place("48.4566809,35.0582073","Hotspot1","hotspot"));
        places.set(4, new Place("48.453001,35.0414132","Испанское латте","cafe"));


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

                Intent intent =new Intent(SearchActivity.this,MapsShop.class);
                intent.putExtra("coors",placeCoors);
                startActivity(intent);
            }
        });
    }
    private void fillPlaces(){

    }

    private void fillCoors(String tag){
        for (Integer i = 1; i < places.size(); i++) {
            if(places.get(i).tag==tag){
                placeCoors.set(i,places.get(i));

            }



        }
    }


}
