

package com.elasticbeanstalk.deckstream.weathermapapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.maps.interfaces.OnAerisMarkerInfoWindowClickListener;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.response.EarthquakesResponse;
import com.hamweather.aeris.response.FiresResponse;
import com.hamweather.aeris.response.StormCellResponse;
import com.hamweather.aeris.response.StormReportsResponse;
import com.hamweather.aeris.tiles.AerisTile;

public class Map extends MapViewFragment implements OnAerisMapLongClickListener, AerisCallback,
        OnAerisMarkerInfoWindowClickListener
{
        LatLng l ;

    public Map(LatLng l) {

        this.l = l;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AerisEngine engine = new AerisEngine();
        engine.initWithKeys("j6KbNuKoeKFTmew7gnHT9", "1KvyCma4gzFSXsoPOhV7I4tW0sW4ddW6WezX309f ", "com.elasticbeanstalk.deckstream.weathermapapp");

        View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);
        mapView.setOnAerisMapLongClickListener(this);
        initMap();
        setHasOptionsMenu(true);
        return view;
    }



    private void initMap() {
        mapView.addLayer(AerisTile.RADSAT);
        mapView.moveToLocation(l, 9);

    }



    @Override
    public void onMapLongClick(double lat, double longitude) {
        // code to handle map long press. i.e. Fetch current conditions?
        // see demo app MapFragment.java
    }

    @Override
    public void onResult(EndpointType endpointType, AerisResponse aerisResponse) {

    }

    @Override
    public void wildfireWindowPressed(FiresResponse firesResponse, AerisMarker aerisMarker) {

    }

    @Override
    public void stormCellsWindowPressed(StormCellResponse stormCellResponse, AerisMarker aerisMarker) {

    }

    @Override
    public void stormReportsWindowPressed(StormReportsResponse stormReportsResponse, AerisMarker aerisMarker) {

    }

    @Override
    public void earthquakeWindowPressed(EarthquakesResponse earthquakesResponse, AerisMarker aerisMarker) {

    }

}