package com.avastronomia.recipecatalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avastronomia.recipecatalog.ui.theme.appSizes

@Composable
fun RecipeCarousel(
    recipeTileDataList: List<RecipeCardData>,
    onClickRecipe: (String, String) -> Unit
) {
    val state = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appSizes.medium.mediumPadding),
        contentPadding = PaddingValues(MaterialTheme.appSizes.large.largePadding),
    ) {
        itemsIndexed(recipeTileDataList) { index, recipeTileData ->
            RecipeCard(
                recipeCardData = recipeTileData,
                onClickRecipe = onClickRecipe
            )
        }
    }
}