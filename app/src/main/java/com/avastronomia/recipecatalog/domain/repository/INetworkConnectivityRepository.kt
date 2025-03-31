package com.avastronomia.recipecatalog.domain.repository

interface INetworkConnectivityRepository {
    fun isNetworkAvailable(): Boolean
}