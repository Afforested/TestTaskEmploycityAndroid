package com.example.testtaskemploycity.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testtaskemploycity.R
import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.model.getDisplayPair
import com.example.testtaskemploycity.presentation.ui.theme.AppColors
import com.example.testtaskemploycity.presentation.utils.formatRate


@Composable
fun CurrencyRateItem(
    currencyRate: CurrencyRate,
    onFavoriteClick: (CurrencyRate) -> Unit,
    modifier: Modifier = Modifier,
    showPair: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = AppColors.color_bg_card)
            .padding(start = 16.dp, end = 12.dp, top = 14.dp, bottom = 12.dp)
    ) {
        Text(
            text = if (showPair) currencyRate.getDisplayPair() else currencyRate.currency.code,
            color = AppColors.color_main_text_default,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currencyRate.rate.formatRate(),
                color = AppColors.color_main_text_default,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                painter = painterResource(
                    id = if (currencyRate.isFavorite) R.drawable.ic_favorites_on else R.drawable.ic_favorites_off
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onFavoriteClick(currencyRate) },
                tint = if (currencyRate.isFavorite) AppColors.color_yellow else AppColors.color_main_secondary
            )
        }
    }
}
