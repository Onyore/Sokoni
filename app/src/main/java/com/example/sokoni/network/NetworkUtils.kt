package com.example.sokoni.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtils {
    companion object{
        private fun getNetworkInfo(context: Context):NetworkInfo?{
            return try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
                cm.activeNetworkInfo
            } catch (e.Exception){
                println("CheckConnectivity Exception:"+ e.message)
                null
            }
        }
        fun isConnected(context: Context):Boolean{
            var info= getNetworkInfo(context)
            return info != null && info.isConnectedOrConnecting
        }
    }
}