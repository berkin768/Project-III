package com.akaydin.berkin.carmonitor;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private LatLng [] latlngArray;
    private ArrayList <LatLng> latlngArrayList;
    private Marker [] markerArray;
    private PolylineOptions polylineOptions;
    private Marker newMarker;
    private int index = 0;
    private int draggedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latlngArray = new LatLng[100];
        markerArray = new Marker[100];
        latlngArrayList = new ArrayList<>();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng izmir = new LatLng(38.3337, 27.2911);
        newMarker = mMap.addMarker(new MarkerOptions().position(izmir).title((index+1) +". Place").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(izmir));

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerDragListener(this);
        latlngArray[index] = newMarker.getPosition();
        markerArray[index] = newMarker;
        index++;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        newMarker = mMap.addMarker(new MarkerOptions().position(latLng).title((index+1)  +". Place").draggable(true));
        latlngArray[index] = newMarker.getPosition();
        markerArray[index] = newMarker;
        index++;

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        latlngArrayList.clear();
        for (int i = 0; i < index; i++) {
            latlngArrayList.add(latlngArray[i]);
        }
        polylineOptions.addAll(latlngArrayList);
        mMap.addPolyline(polylineOptions);
    }


    @Override
    public void onMapClick(LatLng latLng) {
        index = 0;
        draggedIndex = 0;
        mMap.clear();
        latlngArray = new LatLng[100];
        markerArray = new Marker[100];
        latlngArrayList.clear();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        for (int i = 0; i < index; i++) {
            if(marker.equals(markerArray[i])){
                latlngArray[i] = null;
                markerArray[i] = null;
                draggedIndex = i;
                break;
            }
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {}

    @Override
    public void onMarkerDragEnd(Marker marker) {
        mMap.clear();

        latlngArray[draggedIndex] = marker.getPosition();
        markerArray[draggedIndex] = marker ;

        for (int i = 0; i < index; i++) {
            mMap.addMarker(new MarkerOptions().position(latlngArray[i]).title((i+1)  +". Place").draggable(true));
        }

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        latlngArrayList.clear();
        for (int i = 0; i < index; i++) {
            latlngArrayList.add(latlngArray[i]);
        }
        polylineOptions.addAll(latlngArrayList);
        mMap.addPolyline(polylineOptions);
        draggedIndex = 0;
    }
}
