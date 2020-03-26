package com.example.payless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {
    ImageButton cup;
    ImageButton hanger;
    ImageButton wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_act);
        configureNextButton();


    }
    private void configureNextButton(){
        cup = findViewById(R.id.cup_search);
        hanger = findViewById((R.id.hanger_search));
        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this,MapsActivity2.class));
            }
        });
    }
}
