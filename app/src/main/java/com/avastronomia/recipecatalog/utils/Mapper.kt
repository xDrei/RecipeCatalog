package com.avastronomia.recipecatalog.utils

import com.avastronomia.datasource.local.entity.RecipeEntity
import com.avastronomia.recipecatalog.data.remote.dto.RecipeDto
import com.avastronomia.recipecatalog.data.remote.dto.RecipeListDto
import com.avastronomia.recipecatalog.domain.model.Recipe

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        id = id,
        name = name,
        ingredients = ingredients,
        instructions = instructions,
        prepTimeMinutes = prepTimeMinutes,
        cookTimeMinutes = cookTimeMinutes,
        servings = servings,
        difficulty = difficulty,
        cuisine = cuisine,
        caloriesPerServing = caloriesPerServing,
        tags = tags,
        userId = userId,
        image = image,
        rating = rating,
        reviewCount = reviewCount,
        mealType = mealType
    )
}

fun List<RecipeEntity>.toRecipeList(): List<Recipe> {
    return this.map {
        it.toRecipe()
    }
}

fun RecipeListDto.toRecipeEntityList(): List<RecipeEntity> {
    return this.recipeList.map {
        it.toRecipeEntity()
    }
}

fun RecipeDto.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = this.id,
        name = this.name,
        ingredients = this.ingredients,
        instructions = this.instructions,
        prepTimeMinutes = this.prepTimeMinutes ?: 0,
        cookTimeMinutes = this.cookTimeMinutes ?: 0,
        servings = this.servings ?: 0,
        difficulty = this.difficulty,
        cuisine = this.cuisine,
        caloriesPerServing = this.caloriesPerServing,
        tags = this.tags,
        userId = this.userId ?: 0,
        image = this.image,
        rating = this.rating ?: 0.0,
        reviewCount = this.reviewCount ?: 0,
        mealType = this.mealType,
    )
}