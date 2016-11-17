package com.example.nabil.moviestest;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager lm;
    private Location location;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        String url = getAPIString();
        getMovieTheaters(url);
        mapFragment.getMapAsync(this);
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
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(10)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setMyLocationEnabled(true);
    }

    //method that returns the string for the API call
    private String getAPIString(){

        String  url = "https://maps.googleapis.com/maps/api/place/search/json" +
                "?types=movie_theater" +
                "&location=" + latitude + "," + longitude +
                "&radius=10000" +
                "&sensor=false" +
                "&key=AIzaSyBZ9eosFmnHm1A40BPHuzBhIyA_l-LIXZI";
        return url;
    }

    private void getMovieTheaters(String url) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray = null;

                        try {
                            jsonArray = response.getJSONArray("results");
                            int length = jsonArray.length();
                            JSONObject tempJSONObject;
                            JSONObject location;
                            for (int i = 0; i < length; i++){
                                tempJSONObject = jsonArray.getJSONObject(i);
                                location = tempJSONObject.getJSONObject("geometry")
                                        .getJSONObject("location");
                                LatLng loc = new LatLng(location.getDouble("lat")
                                        ,location.getDouble("lng"));
                                mMap.addMarker(new MarkerOptions()
                                        .position(loc)
                                        .title(tempJSONObject.getString("name")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        ApplicationController.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}
