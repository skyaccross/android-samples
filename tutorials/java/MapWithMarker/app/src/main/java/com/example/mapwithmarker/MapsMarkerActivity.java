// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.mapwithmarker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnMapsSdkInitializedCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
// [START maps_marker_on_map_ready]
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private TextView tvNumber;

    private static int number = 0;
    private Marker mMarker = null;
    private static double offset = 0.00001D;
    private static  double SYDNEY_LAT = -33.850000;
    private static  double SYDNEY_LNG = 151.211000;
    private View infoWindowView;

    private GoogleMap mGoogleMap;

    // [START_EXCLUDE]
    // [START maps_marker_get_map_async]
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvNumber = findViewById(R.id.tvNumber);
    }
    // [END maps_marker_get_map_async]
    // [END_EXCLUDE]

    // [START_EXCLUDE silent]
    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    // [END_EXCLUDE]
    // [START maps_marker_on_map_ready_add_marker]
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        infoWindowView = LayoutInflater.from(MapsMarkerActivity.this).inflate(R.layout.view_marker, null);

        UiSettings settings = mGoogleMap.getUiSettings();
        settings.setMyLocationButtonEnabled(false);
        settings.setMapToolbarEnabled(false);
        settings.setRotateGesturesEnabled(false);
        settings.setCompassEnabled(false);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                ((TextView) infoWindowView.findViewById(R.id.marker_content)).setText(marker.getTitle());
                return infoWindowView;
            }

            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                ((TextView) infoWindowView.findViewById(R.id.marker_content)).setText(marker.getTitle());
                return infoWindowView;
            }
        });

        // [START_EXCLUDE silent]
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        // [END_EXCLUDE]
        LatLng sydney = new LatLng(SYDNEY_LAT, SYDNEY_LNG);
        mMarker = googleMap.addMarker(new MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney"));
        // [START_EXCLUDE silent]
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
        // [END_EXCLUDE]
        mMarker.setTitle(number+"");
        showNumber();
    }
    // [END maps_marker_on_map_ready_add_marker]

    private void showNumber(){
        tvNumber.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         number++;
                                         updateMarker();
                                     }
    },20);
    }

    private void updateMarker(){

        SYDNEY_LAT += offset;
        SYDNEY_LNG += offset;

        StringBuilder sb = new StringBuilder();
        sb.append("Datetime:" + formatDatetime(new Date()));
        sb.append("\n");
        sb.append("Index:" + number);
        sb.append("\n");
        sb.append("Lat:" + SYDNEY_LAT);
        sb.append("\n");
        sb.append("Lng:" + SYDNEY_LNG);
        sb.append("\n");
        sb.append("Datetime:" + formatDatetime(new Date()));
        sb.append("\n");
        sb.append("Index:" + number);
        sb.append("\n");
        sb.append("Index:" + number);
        sb.append("\n");
        sb.append("Index:" + number);
        mMarker.setTitle(sb.toString());
        mMarker.showInfoWindow();


        LatLng coordinate = new LatLng(SYDNEY_LAT ,SYDNEY_LNG );
        mMarker.setPosition(coordinate);
        if(SYDNEY_LNG  > 152 || SYDNEY_LNG< 151){
            offset = -offset;
        }

        // 播放的点不在屏幕范围内，移动地图
        VisibleRegion visibleRegion = mGoogleMap.getProjection().getVisibleRegion();
        if (!visibleRegion.latLngBounds.contains(coordinate)) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));

        }

        showNumber();
    }

    private String formatDatetime(Date datetime){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(datetime);
    }
}
// [END maps_marker_on_map_ready]
