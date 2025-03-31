package com.avastronomia.recipecatalog.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.avastronomia.datasource.dao.RecipeDao
import com.avastronomia.datasource.local.entity.RecipeEntity
import com.avastronomia.recipecatalog.data.remote.RecipeApi
import com.avastronomia.recipecatalog.data.repository.NetworkConnectivityRepositoryImpl
import com.avastronomia.recipecatalog.domain.repository.INetworkConnectivityRepository
import com.avastronomia.recipecatalog.utils.AppConstants.LIMIT_10
import com.avastronomia.recipecatalog.utils.toRecipeEntityList

@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator(
    private val recipeDao: RecipeDao,
    private val recipeApi: RecipeApi,
    private val networkConnectivityRepository: INetworkConnectivityRepository
) : RemoteMediator<Int, RecipeEntity>() {
    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }

    private var nextPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeEntity>
    ): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH ->  {
                nextPage = 1
                nextPage
            }
            LoadType.PREPEND -> {
                val firstItem = state.firstItemOrNull()
                if (firstItem == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                val firstItemPage = (firstItem.id / state.config.pageSize)
                if (firstItemPage <= 0) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                firstItemPage
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if(lastItem == null) {
                    1
                } else {
                    nextPage++
                    nextPage
                }
            }
        }

        val pageToLoad = (page - 1) * 10
        if (loadType == LoadType.APPEND ||
            (!networkConnectivityRepository.isNetworkAvailable() && loadType == LoadType.REFRESH)) {
            if (recipeDao.getRecipeCount() > page.times(state.config.pageSize)) {
                return MediatorResult.Success(false)
            }
        } else {
            if(page < 0) {
                return MediatorResult.Success(true)
            }
        }

        return try {
            val recipeApiResponse = recipeApi.getRecipesWithPaging (skip = pageToLoad, limit = LIMIT_10)
            if (loadType == LoadType.REFRESH) {
                recipeDao.deleteAllRecipeFromDatabase()
            }
            recipeDao.insertRecipeListInDatabase(recipeApiResponse.toRecipeEntityList())
            MediatorResult.Success(recipeApiResponse.recipeList.isEmpty())
        } catch(e: Exception) {
            MediatorResult.Error(e)
        }
    }

}