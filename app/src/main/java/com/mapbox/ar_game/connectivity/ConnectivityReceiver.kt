package com.mapbox.ar_game.connectivity

import com.mapbox.mapboxsdk.net.ConnectivityListener
import androidx.annotation.UiThread


/**
 * Created by Satish Kongondhi on 2019-05-06.
 */


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

import java.util.concurrent.CopyOnWriteArrayList

/**
 * ConnectivityReceiver is a BroadcastReceiver that helps you keep track of the connectivity
 * status. When used statically (getSystemConnectivity) the ConnectivityReceiver will always
 * return the connectivity status as reported by the Android system.
 *
 *
 * When instantiating ConnectivityReceiver, you have the option to set a connectedFlag. You can
 * override the connectivity value reported by the system by setting this flag to true or false. If
 * left in its default value (null), ConnectivityReceiver will report the system value.
 *
 *
 * ConnectivityReceiver also lets you subscribe to connecitivity changes using a
 * ConnectivityListener.
 */
class ConnectivityReceiver
/**
 * ConnectivityReceiver constructor
 *
 * @param context Android context. To avoid memory leaks, you might want to pass the application
 * context and make sure you call removeConnectivityUpdates() when you don't need
 * further updates (https://github.com/mapbox/mapbox-gl-native/issues/7176)
 */
    (private val context: Context) : BroadcastReceiver() {
    private val connectivityListeners: CopyOnWriteArrayList<ConnectivityListener> = CopyOnWriteArrayList()
    /**
     * Get the connectedFlag value
     *
     * @return the connectedFlag value, true/false if the connectivity state has ben overriden,
     * null otherwise.
     */
    /**
     * Set the connectedFlag value
     *
     * @param connectedFlag Set it to true/false to override the connectivity state
     */
    var connectedFlag: Boolean? = null
    private var activationCounter: Int = 0

    /**
     * Get the connectivity state. This can be overriden using the connectedFlag.
     *
     * @return the connectivity state
     */
    private val managedConnectivity: Boolean
        get() = if (connectedFlag == null) {
            getSystemConnectivity(context)
        } else connectedFlag!!

    /**
     * Get the connectivity state. This can be overriden using the connectedFlag.
     *
     * @return the connectivity state
     */
    val isConnected: Boolean
        get() = managedConnectivity

    init {
        connectedFlag = null
    }

    fun addConnectivityListener(listener: ConnectivityListener) {
        if (!connectivityListeners.contains(listener)) {
            connectivityListeners.add(listener)
        }
    }

    fun removeConnectivityListener(listener: ConnectivityListener): Boolean {
        return connectivityListeners.remove(listener)
    }

    @UiThread
    fun requestConnectivityUpdates() {
        if (activationCounter == 0) {
            context.registerReceiver(this, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        }
        activationCounter++
    }

    @UiThread
    fun removeConnectivityUpdates() {
        activationCounter--
        if (activationCounter == 0) {
            context.unregisterReceiver(this)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val connected = managedConnectivity
        for (listener in connectivityListeners) {
            listener.onNetworkStateChanged(connected)
        }
    }

    companion object {

        /**
         * Get the connectivity state as reported by the Android system
         *
         * @param context Android context
         * @return the connectivity state as reported by the Android system
         */
        private fun getSystemConnectivity(context: Context): Boolean {
            try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val activeNetwork = cm!!.activeNetworkInfo

                return activeNetwork.isConnectedOrConnecting
            } catch (exception: Exception) {
                return false
            }

        }

        /**
         * Get the connectivity state as reported by the Android system
         *
         * @param context Android context
         * @return the connectivity state as reported by the Android system
         */
        fun isConnected(context: Context): Boolean {
            return getSystemConnectivity(context)
        }
    }
}