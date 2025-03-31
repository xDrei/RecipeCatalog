package com.avastronomia.recipecatalog.ui.theme

data class AppSizes(
    val extraSmall: ExtraSmallSize = ExtraSmallSize,
    val small: SmallSize = SmallSize,
    val medium: MediumSize = MediumSize,
    val large: LargeSize = LargeSize,
    val extraLarge: ExtraLargeSize = ExtraLargeSize,
)

val LocalAppSizes = androidx.compose.runtime.staticCompositionLocalOf { AppSizes() }

val androidx.compose.material3.MaterialTheme.appSizes: AppSizes
    @androidx.compose.runtime.Composable
    @androidx.compose.runtime.ReadOnlyComposable
    get() = LocalAppSizes.current