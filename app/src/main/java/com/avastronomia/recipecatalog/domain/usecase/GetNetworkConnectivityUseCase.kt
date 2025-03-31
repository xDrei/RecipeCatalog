package com.avastronomia.recipecatalog.domain.usecase

import com.avastronomia.recipecatalog.domain.repository.INetworkConnectivityRepository
import javax.inject.Inject

class GetNetworkConnectivityUseCase @Inject constructor(
    private val networkConnectivityRepositoryImpl: INetworkConnectivityRepository
) {
    operator fun invoke(): Boolean  {
        return networkConnectivityRepositoryImpl.isNetworkAvailable()
    }
}