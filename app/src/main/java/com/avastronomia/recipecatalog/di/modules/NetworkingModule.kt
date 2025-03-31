package com.avastronomia.recipecatalog.di.modules

import android.content.Context
import com.avastronomia.datasource.dao.RecipeDao
import com.avastronomia.datasource.local.LocalDataSource
import com.avastronomia.recipecatalog.data.remote.RecipeApi
import com.avastronomia.recipecatalog.data.repository.NetworkConnectivityRepositoryImpl
import com.avastronomia.recipecatalog.data.repository.RecipeRepositoryImpl
import com.avastronomia.recipecatalog.domain.repository.INetworkConnectivityRepository
import com.avastronomia.recipecatalog.domain.repository.IRecipeRepository
import com.avastronomia.recipecatalog.utils.AppConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {

    @Provides
    @Singleton
    fun providesRecipeApi(): RecipeApi {
        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    HttpLoggingInterceptor.Level.BODY
                }
            )

            addInterceptor(
                Interceptor { chain ->
                    //TODO: Added to simulate slow network
                    runBlocking {
                        delay(2000)
                    }
                    val request = chain.request().newBuilder().build()
                    chain.proceed(request)
                }
            )
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRecipeRepository(
        recipeApi: RecipeApi,
        recipeDao: RecipeDao,
        localDataSource: LocalDataSource,
        networkConnectivityRepository: INetworkConnectivityRepository
    ) : IRecipeRepository {
        return RecipeRepositoryImpl(
            recipeApi = recipeApi,
            recipeDao = recipeDao,
            localDataSource = localDataSource,
            networkConnectivityRepository = networkConnectivityRepository
        )
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityRepository(
        @ApplicationContext context: Context
    ): INetworkConnectivityRepository {
        return NetworkConnectivityRepositoryImpl(context)
    }
}