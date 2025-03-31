package com.avastronomia.recipecatalog.domain.model

data class Recipe (
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
    val userId: Int = 0,
    val image: String? = null,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val mealType: List<String> = listOf(),
)