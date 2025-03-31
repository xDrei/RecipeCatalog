package com.avastronomia.recipecatalog.utils

import com.avastronomia.recipecatalog.components.RecipeCardData
import com.avastronomia.recipecatalog.components.RecipeTileData
import com.avastronomia.recipecatalog.domain.model.Recipe
import com.avastronomia.recipecatalog.utils.AppConstants.AND
import com.avastronomia.recipecatalog.utils.AppConstants.HOUR
import com.avastronomia.recipecatalog.utils.AppConstants.HOURS
import com.avastronomia.recipecatalog.utils.AppConstants.MINUTE
import com.avastronomia.recipecatalog.utils.AppConstants.MINUTES

fun Int.toTime(): String {
    if (this == 0) {
        return "N/A"
    }

    val hours = this / 60
    val minutes = this % 60

    val minutesLabel = if (minutes == 1) MINUTE else MINUTES
    val stringBuilder = StringBuilder()

    if (hours > 0) {
        val hoursLabel = if (hours == 1) HOUR else HOURS
        stringBuilder.append(hours)
        stringBuilder.append(hoursLabel)
        if (minutes > 0) {
            stringBuilder.append(AND)
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