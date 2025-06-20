package com.ddesk.sddemoapp.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import kotlin.math.abs

fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false

}



fun calculateCompatibility(
    currentUserAge: Int,
    currentUserCity: String,
    matchAge: Int,
    matchCity: String
): Float {
    val ageDiff = abs(currentUserAge - matchAge).toFloat()
    val ageScore = when {
        ageDiff <= 2f -> 1f
        ageDiff <= 5f -> 0.8f
        ageDiff <= 8f -> 0.6f
        ageDiff <= 12f -> 0.4f
        else -> 0.2f
    } * 0.6f

    val locationScore = if (currentUserCity.equals(matchCity, ignoreCase = true)) {
        1f
    } else {
        0f
    } * 0.4f

    return (ageScore + locationScore).coerceIn(0f, 1f)
}