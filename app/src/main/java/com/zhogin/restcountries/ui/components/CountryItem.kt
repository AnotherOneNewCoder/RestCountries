package com.zhogin.restcountries.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zhogin.restcountries.domain.model.Country

@Composable
fun CountryItem(
    modifier: Modifier = Modifier,
    county: Country,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable(
                onClick = onClick
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = county.flagUri,
            contentDescription = "Флаг ${county.name}",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(48.dp)
                .aspectRatio(1.5f)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = county.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}