package com.avastronomia.recipecatalog.presentation.screens.recipedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.avastronomia.recipecatalog.R
import com.avastronomia.recipecatalog.components.AlertNotification
import com.avastronomia.recipecatalog.components.AppToolbar
import com.avastronomia.recipecatalog.domain.model.Recipe
import com.avastronomia.recipecatalog.ui.theme.appSizes
import com.avastronomia.recipecatalog.utils.toTime
import com.avastronomia.recipecatalog.components.CheckBoxWithText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    recipeName: String,
    onBackButtonClick: () -> Unit,
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    viewModel.callGetRecipeDetails(recipeId)


    Scaffold(
        topBar = {
            AppToolbar(
                title = recipeName,
                onBackButtonClick = onBackButtonClick,
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                if (state.noNetwork) {
                    AlertNotification(
                        message = stringResource(R.string.internet_error)
                    )
                }

                state.recipe?.let { safeRecipe ->
                    RecipeDetailsScreenContents(safeRecipe)
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun RecipeDetailsScreenContents(
    recipe: Recipe
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = MaterialTheme.appSizes.medium.mediumPadding,
                start = MaterialTheme.appSizes.large.largePadding,
                end = MaterialTheme.appSizes.large.largePadding,
            )
    ) {
        item {
            Box(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.appSizes.medium.mediumPadding
                    )
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(MaterialTheme.appSizes.medium.cornerRadius))
            ) {
                AsyncImage(
                    model = recipe.image,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.img_tile_placeholder),
                    error = painterResource(R.drawable.img_tile_placeholder),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(MaterialTheme.appSizes.medium.mediumPadding))

            Text(
                text = recipe.name.orEmpty(),
                style = MaterialTheme.typography.headlineSmall.copy(
                    MaterialTheme.colorScheme.onBackground
                )
            )

            BodyText(
                title = stringResource(R.string.cuisine_label),
                content = recipe.cuisine.orEmpty()
            )

            BodyText(
                title = stringResource(R.string.rating_label),
                content = "${recipe.rating}/5"
            )

            BodyText(
                title = stringResource(R.string.difficulty_label),
                content = recipe.difficulty.orEmpty()
            )

            BodyText(
                title = stringResource(R.string.prep_time_label),
                content = (recipe.prepTimeMinutes).toTime()
            )

            BodyText(
                title = stringResource(R.string.cook_time_label),
                content = (recipe.cookTimeMinutes).toTime()
            )
        }

        item {
            ListContentWithCheckBox(
                header = stringResource(R.string.ingredients_header),
                items = recipe.ingredients,
            )
        }

        item {
            ListContentWithCheckBox(
                header = stringResource(R.string.instructions_header),
                items = recipe.instructions,
            )
        }
    }
}

@Composable
fun BodyText(
    title: String = "",
    content: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = MaterialTheme.appSizes.small.smallPadding
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Composable
fun ListContentWithCheckBox(
    header: String = "",
    items: List<String> = listOf(),
) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.appSizes.medium.mediumPadding)
    ) {
        Spacer(modifier = Modifier.padding(MaterialTheme.appSizes.medium.mediumPadding))

        Text(
            text = header,
            style = MaterialTheme.typography.titleMedium.copy (
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                CheckBoxWithText(item)
            }
        }
    }
}

