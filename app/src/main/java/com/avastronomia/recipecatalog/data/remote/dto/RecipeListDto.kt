package com.avastronomia.recipecatalog.data.remote.dto

import com.avastronomia.datasource.local.entity.RecipeEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeListDto(
    @SerializedName("recipes")
    val recipeList: List<RecipeDto> = listOf(),
): Serializable

data class RecipeDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("ingredients")
    val ingredients: List<String> = listOf(),
    @SerializedName("instructions")
    val instructions: List<String> = listOf(),
    @SerializedName("prepTimeMinutes")
    val prepTimeMinutes: Int? = null,
    @SerializedName("cookTimeMinutes")
    val cookTimeMinutes: Int? = null,
    @SerializedName("servings")
    val servings: Int? = null,
    @SerializedName("difficulty")
    val difficulty: String? = null,
    @SerializedName("cuisine")
    val cuisine: String? = null,
    @SerializedName("caloriesPerServing")
    val caloriesPerServing: Int? = null,
    @SerializedName("tags")
    val tags: List<String> = listOf(),
    //TODO check if this is usable else delete it
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("reviewCount")
    val reviewCount: Int? = null,
    @SerializedName("mealType")
    val mealType: List<String> = listOf(),
): Serializable