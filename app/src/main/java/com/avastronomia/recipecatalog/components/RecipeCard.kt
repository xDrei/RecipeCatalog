package com.avastronomia.recipecatalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.avastronomia.recipecatalog.R
import com.avastronomia.recipecatalog.ui.theme.appSizes

@Composable
fun RecipeCard(
    recipeCardData: RecipeCardData,
    onClickRecipe: (String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .height(MaterialTheme.appSizes.medium.cardHeight)
            .width(MaterialTheme.appSizes.medium.cardWidth)
            .clickable {
                onClickRecipe(recipeCardData.id, recipeCardData.name)
            },
        shape = RoundedCornerShape(MaterialTheme.appSizes.large.cornerRadius),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = recipeCardData.imageUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img_card_placeholder),
                error = painterResource(R.drawable.img_card_placeholder),
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillHeight
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            ) {

            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.appSizes.medium.mediumPadding),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = recipeCardData.name,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontStyle = MaterialTheme.typography.titleMedium.fontStyle
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

data class RecipeCardData(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
)
