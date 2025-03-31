package com.avastronomia.recipecatalog.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = Route.SPLASH_SCREEN.name)
    object OverviewScreen : Screen(route = Route.OVERVIEW_SCREEN.name)
    object RecipeDetailsScreen : Screen(route = Route.RECIPE_DETAILS_SCREEN.name)
}

enum class Route {
    SPLASH_SCREEN,
    OVERVIEW_SCREEN,
    RECIPE_DETAILS_SCREEN
}

enum class ScreenArgs(name: String) {
    RECIPE_ID("recipeId"),
    RECIPE_NAME("recipeName")
}