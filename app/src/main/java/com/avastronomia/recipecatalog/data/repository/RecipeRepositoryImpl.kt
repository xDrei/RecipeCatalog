package com.avastronomia.recipecatalog.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.avastronomia.datasource.dao.RecipeDao
import com.avastronomia.datasource.local.LocalDataSource
import com.avastronomia.datasource.local.entity.RecipeEntity
import com.avastronomia.recipecatalog.data.paging.RecipeRemoteMediator
import com.avastronomia.recipecatalog.data.remote.RecipeApi
import com.avastronomia.recipecatalog.domain.model.Recipe
import com.avastronomia.recipecatalog.domain.repository.INetworkConnectivityRepository
import com.avastronomia.recipecatalog.domain.repository.IRecipeRepository
import com.avastronomia.recipecatalog.utils.Resource
import com.avastronomia.recipecatalog.utils.toRecipe
import com.avastronomia.recipecatalog.utils.toRecipeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val recipeDao: RecipeDao,
    private val localDataSource: LocalDataSource,
    private val networkConnectivityRepository: INetworkConnectivityRepository
): IRecipeRepository{
    override suspend fun getPopularRecipe(): Flow<Resource<List<Recipe>>> = flow {
        try {
            emit(Resource.Loading())
            val popularRecipeFromDatabase = getPopularRecipeFromDatabase()

            popularRecipeFromDatabase?.let {
                emit(Resource.Success(popularRecipeFromDatabase.toRecipeList()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

    override suspend fun getRecipeDetails(recipeId: String): Flow<Resource<Recipe>> = flow {
        try {
            emit(Resource.Loading())

            val recipeFromDatabase = getRecipeFromDatabase(recipeId)
            recipeFromDatabase?.let {
                emit(Resource.Success(recipeFromDatabase.toRecipe()))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeWithPager(): Flow<PagingData<Recipe>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                recipeDao.getRecipeWithPaging()
            },
            remoteMediator = RecipeRemoteMediator(
                recipeDao = recipeDao,
                recipeApi = recipeApi,
                networkConnectivityRepository = networkConnectivityRepository
            )
        ).flow.map { pagingData ->
            pagingData.map { recipeEntity ->
                recipeEntity.toRecipe()
            }
        }
    }

    private suspend fun getRecipeFromDatabase(recipeId: String): RecipeEntity? =
        withContext(Dispatchers.IO) {
            localDataSource.getRecipeFromDatabase(recipeId)
        }

    private suspend fun getPopularRecipeFromDatabase(): List<RecipeEntity>? =
        withContext(Dispatchers.IO) {
            val recipeList = localDataSource.getPopularRecipeFromDatabase()
            if (recipeList.isEmpty()) {
                null
            } else {
                recipeList
            }
        }
}