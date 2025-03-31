package com.avastronomia.datasource.local

import androidx.paging.PagingSource
import com.avastronomia.datasource.local.entity.RecipeEntity

interface LocalDataSource {

    suspend fun insertRecipeListInDatabase(recipeEntityList: List<RecipeEntity>)

    suspend fun getRecipeFromDatabase(recipeId: String): RecipeEntity?

    suspend fun getPopularRecipeFromDatabase(): List<RecipeEntity>

    suspend fun deleteAllRecipeFromDatabase()
}