package com.example.mapwithmarker;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapsSdkInitializedCallback;

public class MapsApplication  extends Application implements OnMapsSdkInitializedCallback {

    private static final String TAG = "GpsApplication";

    @Override
    public void onCreate() {
        Log.i(TAG, "###### APPLICATION CREATE");
        super.onCreate();
        // When we choose the LATEST renderer, the infowindow will flicker.
        //  and then cause ANR after about 2000 coordinates shown.
        // When we choose the LEGACY renderer, the infowidow wont flicker ,
        // and the infowindow will be stable and smooth.
        // and NO ANR even we played over 10000 coordinates.
        MapsInitializer.initialize(getApplicationContext(), MapsInitializer.Renderer.LATEST, this);

    }


    @Override
    public void onMapsSdkInitialized(@NonNull MapsInitializer.Renderer renderer) {
        switch(renderer){
            case LATEST:
                Log.i(TAG, "######The latest version of the renderer is used");
                break;
            case LEGACY:
                Log.i(TAG, "######The legacy version of the renderer is used");
                break;
        }
    }
}
