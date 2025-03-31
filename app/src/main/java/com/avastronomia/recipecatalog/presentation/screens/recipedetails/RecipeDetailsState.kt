package com.avastronomia.recipecatalog.presentation.screens.recipedetails

import com.avastronomia.recipecatalog.domain.model.Recipe

data class RecipeDetailsState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val error: String? = null,
    val noNetwork: Boolean = false
)