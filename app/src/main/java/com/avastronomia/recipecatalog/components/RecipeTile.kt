package com.avastronomia.recipecatalog.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.avastronomia.recipecatalog.R
import com.avastronomia.recipecatalog.ui.theme.appSizes
import com.avastronomia.recipecatalog.utils.toTime
import java.util.Locale

@Composable
fun RecipeTile(
    recipeTileData: RecipeTileData,
    onClick: (String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.appSizes.large.largePadding,
                vertical = MaterialTheme.appSizes.small.smallPadding
            )
            .height(MaterialTheme.appSizes.medium.billboardHeight)
            .clickable {
                onClick(recipeTileData.id, recipeTileData.name)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(MaterialTheme.appSizes.small.cornerRadius),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.appSizes.medium.mediumPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = recipeTileData.imageUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img_tile_placeholder),
                error = painterResource(R.drawable.img_tile_placeholder),
                modifier = Modifier
                    .height(144.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(MaterialTheme.appSizes.extraSmall.cornerRadius)),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.width(MaterialTheme.appSizes.medium.mediumPadding))

            Column(
                modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ) {
                Text(
                    text = recipeTileData.cuisine.uppercase(),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(MaterialTheme.appSizes.small.smallPadding))

                Text(
                    text = recipeTileData.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    TileDetails(
                        id = R.drawable.img_timer,
                        text = recipeTileData.prepTime.toTime()
                    )

                    TileDetails(
                        id = R.drawable.img_cooking,
                        text = recipeTileData.cookTime.toTime()
                    )
                }
            }
        }
    }
}

@Composable
fun TileDetails(
    @DrawableRes id: Int,
    text: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appSizes.medium.mediumPadding),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}


data class RecipeTileData(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val cuisine: String = "",
    val prepTime: Int = 0,
    val cookTime: Int = 0,
)