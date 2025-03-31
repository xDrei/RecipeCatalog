package com.avastronomia.recipecatalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.avastronomia.recipecatalog.R
import com.avastronomia.recipecatalog.ui.theme.appSizes

@Composable
fun AlertNotification (
    message: String = "",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error)
            .padding(
                vertical = MaterialTheme.appSizes.medium.mediumPadding,
                horizontal = MaterialTheme.appSizes.large.largePadding
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appSizes.medium.mediumPadding),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            painter = painterResource(id = R.drawable.img_error),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surface
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.surface
            )
        )
    }
}