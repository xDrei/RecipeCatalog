package com.avastronomia.recipecatalog.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.avastronomia.recipecatalog.domain.repository.INetworkConnectivityRepository
import javax.inject.Inject

class NetworkConnectivityRepositoryImpl @Inject constructor(
    private val context: Context
) : INetworkConnectivityRepository {
    override fun isNetworkAvailable(): Boolean {
        val connectivityManager
            = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}