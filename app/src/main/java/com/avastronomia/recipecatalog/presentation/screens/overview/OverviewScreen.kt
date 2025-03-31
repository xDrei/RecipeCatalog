package com.avastronomia.recipecatalog.presentation.screens.overview

import com.avastronomia.recipecatalog.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.avastronomia.recipecatalog.components.AlertNotification
import com.avastronomia.recipecatalog.components.RecipeCardData
import com.avastronomia.recipecatalog.components.RecipeCarousel
import com.avastronomia.recipecatalog.components.RecipeTile
import com.avastronomia.recipecatalog.domain.model.Recipe
import com.avastronomia.recipecatalog.ui.theme.appSizes
import com.avastronomia.recipecatalog.utils.convertToRecipeCardDataList
import com.avastronomia.recipecatalog.utils.convertToRecipeTileData
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onClickRecipe: (String, String) -> Unit,
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val recipes = viewModel.recipes.collectAsLazyPagingItems()
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing = remember { mutableStateOf(false) }

    LaunchedEffect((recipes.itemCount > 0)) {
        viewModel.callGetPopularRecipe()
    }

    LaunchedEffect(isRefreshing.value) {
        delay(1000)
        isRefreshing.value = false
    }

    Scaffold { scaffoldPadding ->
        Box (
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                if (state.value.noNetwork) {
                    AlertNotification(
                        message = stringResource(R.string.internet_error)
                    )
                }

                PullToRefreshBox(
                    modifier = Modifier.fillMaxSize(),
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing.value,
                    onRefresh = {
                        isRefreshing.value = true
                        recipes.refresh()
                    }
                ) {
                    OverviewScreenContent(
                        popularRecipeCardList = state.value.popularRecipes.convertToRecipeCardDataList(),
                        recipePagingItem = recipes,
                        onClickRecipe = onClickRecipe
                    )
                }
            }

            if (recipes.loadState.prepend is LoadState.Loading || recipes.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun OverviewScreenContent(
    popularRecipeCardList: List<RecipeCardData>,
    recipePagingItem: LazyPagingItems<Recipe>,
    onClickRecipe: (String, String) -> Unit,
) {
    LazyColumn {
        item {
            PopularRecipeSection(
                recipeCardDataList = popularRecipeCardList,
                onClickRecipe = onClickRecipe
            )
        }

        if (recipePagingItem.loadState.refresh is LoadState.NotLoading) {
            if (recipePagingItem.itemCount != 0) {
                item {
                    Spacer(modifier = Modifier.padding(MaterialTheme.appSizes.medium.mediumPadding))

                    Text(
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.appSizes.large.largePadding),
                        text = stringResource(R.string.other_recipe_header),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Spacer(modifier = Modifier.padding(MaterialTheme.appSizes.small.smallPadding))
                }

                items(
                    count = recipePagingItem.itemCount,
                    key = recipePagingItem.itemKey {
                        it.id
                    },
                    contentType = recipePagingItem.itemContentType { "contenttype" }
                ) { index ->
                    recipePagingItem[index]?.let { safeRecipe ->
                        RecipeTile(
                            recipeTileData = safeRecipe.convertToRecipeTileData(),
                            onClick = onClickRecipe
                        )
                    }
                }

                if (recipePagingItem.loadState.append == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.appSizes.medium.mediumPadding)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopularRecipeSection(
    recipeCardDataList: List<RecipeCardData> = listOf(),
    onClickRecipe: (String, String) -> Unit,
) {
    Spacer(modifier = Modifier.padding(MaterialTheme.appSizes.medium.mediumPadding))

    if (recipeCardDataList.isNotEmpty()) {
        Column {
            Text(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.appSizes.large.largePadding),
                text = stringResource(R.string.popular_recipe_header),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            RecipeCarousel(
                recipeTileDataList = recipeCardDataList,
                onClickRecipe = onClickRecipe
            )
        }
    }
}