package com.elasticbeanstalk.deckstream.weathermapapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.model.LatLng;

public class MapView extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        Bundle extras = getIntent().getExtras();
           Double    latitude;
        Double longitude ;


        if (savedInstanceState == null) {
            if(extras == null) {
                latitude= null;
                longitude=null;
            } else {

                latitude= extras.getDouble("latitude");
                longitude= extras.getDouble("longitude");
            }
        } else {
            latitude= (Double) savedInstanceState.getSerializable("latitude");
            longitude= (Double) savedInstanceState.getSerializable("longitude");

        }
        LatLng l = new LatLng(latitude,longitude);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new Map(l))
                .commit();




    }
}