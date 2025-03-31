package com.avastronomia.recipecatalog.domain.repository

import androidx.paging.PagingData
import com.avastronomia.recipecatalog.domain.model.Recipe
import com.avastronomia.recipecatalog.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IRecipeRepository {
    suspend fun getPopularRecipe(): Flow<Resource<List<Recipe>>>

    suspend fun getRecipeDetails(recipeId: String): Flow<Resource<Recipe>>

    fun getRecipeWithPager(): Flow<PagingData<Recipe>>
}