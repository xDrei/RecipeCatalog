package com.avastronomia.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avastronomia.datasource.local.TableConstants.RECIPE_TABLE_NAME

@Entity(tableName = RECIPE_TABLE_NAME)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String? = null,
    val ingredients: List<String> = listOf(),
    val instructions: List<String> = listOf(),
    val prepTimeMinutes: Int = 0,
    val cookTimeMinutes: Int = 0,
    val servings: Int = 0,
    val difficulty: String? = null,
    val cuisine: String? = null,
    val caloriesPerServing: Int? = null,
    val tags: List<String> = listOf(),
    //TODO check if this is usable else delete it
    val userId: Int = 0,
    val image: String? = null,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val mealType: List<String> = listOf(),
)