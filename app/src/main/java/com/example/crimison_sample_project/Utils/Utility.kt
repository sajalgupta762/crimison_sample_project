package com.example.crimison_sample_project.Utils

import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat
import android.content.Context;

class Utility {

    companion object {

        private lateinit var context: Context
        fun isConnectingToInternet(context: Context): Boolean {
            val connectivity = context.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val info = connectivity.allNetworkInfo
                if (info != null)
                    for (i in info)
                        if (i.state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
            }
            return false
        }
    }}