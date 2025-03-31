package com.avastronomia.recipecatalog.presentation.screens.overview

import androidx.paging.PagingData
import com.avastronomia.recipecatalog.domain.model.Recipe


data class OverviewScreenState(
    val isLoading: Boolean = false,
    val popularRecipes: List<Recipe> = listOf(),
    val error: String? = null,
    val noNetwork: Boolean = false
)
