package com.avastronomia.datasource.local

import android.content.Context
import androidx.paging.PagingSource
import com.avastronomia.datasource.local.entity.RecipeEntity
import dagger.hilt.android.qualifiers.ApplicationContext

class LocalDataSourceImpl(
    @ApplicationContext context: Context
): LocalDataSource {
    private val appDatabase: AppDatabase = AppDatabase.getDatabase(context)

    override suspend fun insertRecipeListInDatabase(recipeEntityList: List<RecipeEntity>) {
        appDatabase.getRecipeDao()
            .insertRecipeListInDatabase(recipeEntityList)
    }

    override suspend fun getRecipeFromDatabase(recipeId: String): RecipeEntity? {
        return appDatabase.getRecipeDao().getRecipesFromDatabase(recipeId)
    }

    override suspend fun getPopularRecipeFromDatabase(): List<RecipeEntity> {
        return appDatabase.getRecipeDao().getPopularRecipesFromDatabase()
    }

    override suspend fun deleteAllRecipeFromDatabase() {
        appDatabase.getRecipeDao().deleteAllRecipeFromDatabase()
    }
}