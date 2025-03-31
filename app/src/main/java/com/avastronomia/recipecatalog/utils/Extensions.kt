package com.avastronomia.recipecatalog.utils

import com.avastronomia.recipecatalog.components.RecipeCardData
import com.avastronomia.recipecatalog.components.RecipeTileData
import com.avastronomia.recipecatalog.domain.model.Recipe

fun Int.toTime(): String {
    val hours = this / 60
    val minutes = this % 60

    val minutesLabel = if (minutes == 1) " minute" else " minutes"
    val stringBuilder = StringBuilder()

    if (hours > 0) {
        val hoursLabel = if (hours == 1) " hour" else " hours"
        stringBuilder.append(hours)
        stringBuilder.append(hoursLabel)
        if (minutes > 0) {
            stringBuilder.append(" and ")
        }
    }
    if (minutes > 0) {
        stringBuilder.append(minutes)
        stringBuilder.append(minutesLabel)
    }

    return stringBuilder.toString()
}

fun List<Recipe>.convertToRecipeCardDataList(): List<RecipeCardData> {
    return this.map {
        RecipeCardData(
            id = it.id.toString(),
            name = it.name.orEmpty(),
            imageUrl = it.image.orEmpty()
        )
    }
}

fun List<Recipe>.convertToRecipeTileDataList(): List<RecipeTileData> {
    return this.map {
        RecipeTileData(
            id = it.id.toString(),
            name = it.name.orEmpty(),
            imageUrl = it.image.orEmpty(),
            cuisine = it.cuisine.orEmpty(),
            prepTime = it.prepTimeMinutes,
            cookTime = it.cookTimeMinutes,
        )
    }
}

fun Recipe.convertToRecipeTileData(): RecipeTileData {
    return RecipeTileData(
        id = this.id.toString(),
        name = this.name.orEmpty(),
        imageUrl = this.image.orEmpty(),
        cuisine = this.cuisine.orEmpty(),
        prepTime = this.prepTimeMinutes,
        cookTime = this.cookTimeMinutes,
    )
}