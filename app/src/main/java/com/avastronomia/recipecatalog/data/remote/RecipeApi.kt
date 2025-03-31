package com.avastronomia.recipecatalog.data.remote

import com.avastronomia.recipecatalog.data.remote.dto.RecipeListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipesWithPaging(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): RecipeListDto
}