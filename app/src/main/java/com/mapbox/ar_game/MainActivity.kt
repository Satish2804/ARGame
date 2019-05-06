package com.mapbox.ar_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        map_view.onCreate(savedInstanceState)
        map_view.getMapAsync(this)


    }


    override fun onMapReady(mapboxMap: MapboxMap) {

        mapboxMap.setStyle(Style.MAPBOX_STREETS, Style.OnStyleLoaded {

        })
    }

    override fun onStart() {
        super.onStart()
        map_view.onStart()

    }

    override fun onStop() {
        super.onStop()
        map_view.onStop()

    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()

    }


    override fun onResume() {
        super.onResume()
        map_view.onResume()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map_view.onSaveInstanceState(outState)

    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_view.onLowMemory()

    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()

    }
}
