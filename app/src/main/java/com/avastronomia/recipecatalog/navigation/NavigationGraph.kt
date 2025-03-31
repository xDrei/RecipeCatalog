package com.avastronomia.recipecatalog.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.avastronomia.recipecatalog.navigation.Route.OVERVIEW_SCREEN
import com.avastronomia.recipecatalog.navigation.Route.RECIPE_DETAILS_SCREEN
import com.avastronomia.recipecatalog.presentation.screens.overview.OverviewScreen
import com.avastronomia.recipecatalog.presentation.screens.recipedetails.RecipeDetailsScreen
import com.avastronomia.recipecatalog.utils.AppConstants.NAV_ANIMATION_DURATION

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.OverviewScreen.route) {
        composable(
            route = Screen.OverviewScreen.route,
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(NAV_ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(NAV_ANIMATION_DURATION)
                )
            }
        ) {
            OverviewScreen(
                onClickRecipe = { id, name ->
                    navController.navigate(
                        Screen.RecipeDetailsScreen.route + "/$id" + "/${name}"
                    )
                }
            )
        }

        composable(
            route = "${Screen.RecipeDetailsScreen.route}/{${ScreenArgs.RECIPE_ID}}/{${ScreenArgs.RECIPE_NAME}}",
            arguments = listOf(
                navArgument(name = "${ScreenArgs.RECIPE_ID}") {
                    type = NavType.StringType
                },
                navArgument(name = "${ScreenArgs.RECIPE_NAME}") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(NAV_ANIMATION_DURATION)
                )
            },
            exitTransition = null,
        ) {
            val recipeId = it.arguments?.getString("${ScreenArgs.RECIPE_ID}")
            val recipeName = it.arguments?.getString("${ScreenArgs.RECIPE_NAME}")

            RecipeDetailsScreen(
                recipeId = recipeId.orEmpty(),
                recipeName = recipeName.orEmpty(),
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )

        }
    }
}