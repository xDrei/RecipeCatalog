package com.avastronomia.datasource.dao


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avastronomia.datasource.local.TableConstants.RECIPE_TABLE_NAME
import com.avastronomia.datasource.local.entity.RecipeEntity

@Dao
interface RecipeDao: BaseDao<RecipeEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeListInDatabase(recipeEntityList: List<RecipeEntity>)

    @Query("SELECT * FROM ${RECIPE_TABLE_NAME}")
    fun getRecipeListFromDatabase(): List<RecipeEntity>

    @Query("SELECT * FROM ${RECIPE_TABLE_NAME} WHERE id = :recipeId")
    fun getRecipesFromDatabase(recipeId: String): RecipeEntity?

    @Query("SELECT * FROM ${RECIPE_TABLE_NAME} ORDER BY rating DESC LIMIT 10")
    suspend fun getPopularRecipesFromDatabase(): List<RecipeEntity>

    @Query("DELETE FROM ${RECIPE_TABLE_NAME}")
    suspend fun deleteAllRecipeFromDatabase()

    @Query("SELECT * FROM ${RECIPE_TABLE_NAME}")
    fun getRecipeWithPaging(): PagingSource<Int, RecipeEntity>

    @Query("SELECT COUNT(*) FROM ${RECIPE_TABLE_NAME}")
    suspend fun getRecipeCount(): Int

}