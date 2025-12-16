package com.zhogin.restcountries.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.ui.theme.SecondGradient

@Composable
fun CountryItem(
    modifier: Modifier = Modifier,
    county: Country,

    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = county.name,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 30.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                ,
                lineHeight = 38.sp
            )
            Surface(
                modifier = Modifier
                    .size(width = 100.dp, height = 60.dp)
                    //.padding(top = 8.dp)
                ,
                shape = RoundedCornerShape(4.dp),
                color = Color.LightGray
            ) {
                AsyncImage(
                    model = county.flagUri,
                    contentDescription = "Флаг ${county.name}",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()

                )
            }
        }

        Spacer(Modifier.height(24.dp))
        InfoSelection(
            label = "Continent",
            value = county.region ?: "Unknown"
        )
        InfoSelection(
            label = "Capital",
            value = county.capital ?: "Unknown"
        )
        InfoSelection(
            label = "Population",
            value = county.population.toString()
        )
        Text(
            text = "Other details",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = SecondGradient.copy(
                alpha = 0.35f
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Emoji flag",
                    style = MaterialTheme.typography.bodyLarge
                )
                AsyncImage(
                    model = county.flagUri,
                    contentDescription = "Флаг ${county.name}",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(24.dp)
                        .aspectRatio(1.5f)

                )
            }
        }

    }
}