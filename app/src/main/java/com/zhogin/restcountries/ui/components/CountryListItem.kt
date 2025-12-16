package com.zhogin.restcountries.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.ui.theme.IconDetails
import com.zhogin.restcountries.ui.theme.TextDark


@Composable
fun CountryListItem(
    modifier: Modifier = Modifier,
    county: Country,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = {onClick(county.name)}),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(
                alpha = 0.35f
            ),
            contentColor = TextDark,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp)
            ,
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
                style = MaterialTheme.typography.titleMedium,
                color = TextDark
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = IconDetails
            )
        }
    }



}