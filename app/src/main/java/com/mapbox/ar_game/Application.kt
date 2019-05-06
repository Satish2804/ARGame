package com.mapbox.ar_game

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

/**
 * Created by Satish Kongondhi on 2019-05-06.
 */
public class Application : Application() {


    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(this, getString(R.string.mapbox_token))
    }
}