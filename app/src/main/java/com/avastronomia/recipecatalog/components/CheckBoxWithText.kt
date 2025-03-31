package com.avastronomia.recipecatalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.avastronomia.recipecatalog.ui.theme.appSizes

@Composable
fun CheckBoxWithText(
    text: String
) {
    var checked by remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appSizes.small.smallPadding),
    ) {
        Checkbox(
            modifier = Modifier
                .height(MaterialTheme.appSizes.medium.checkBoxSize)
                .width(MaterialTheme.appSizes.medium.checkBoxSize),
            checked = checked,
            onCheckedChange = { checked = it }
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}