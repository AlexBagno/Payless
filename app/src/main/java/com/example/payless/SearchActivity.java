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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_act);
        configureNextButton();
        places = new ArrayList<Place>();


    }
    private void configureNextButton(){
        cup = findViewById(R.id.cup_search);
        hanger = findViewById((R.id.hanger_search));
        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SearchActivity.this,MapsActivity2.class);
                intent.putExtra("coors",placeCoors);
                startActivity(intent);
            }
        });
    }
    private void fillPlaces(){

    }
    private void fillCoors(){
        for (Integer i = 0; i < places.size(); i++) {
            String temp = i.toString();
            placeCoors[i]=places.get(i).coor;

        }
    }
}
