package com.example.testtaskemploycity.presentation.rates.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.testtaskemploycity.R
import com.example.testtaskemploycity.presentation.ui.theme.AppColors


@Composable
fun BaseCurrencySelector(
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val currencies = listOf(stringResource(R.string.usd), stringResource(R.string.eur),
        stringResource(R.string.jpy)
    )
    var selectorWidth by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    Box(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .onGloballyPositioned { coordinates ->
                    selectorWidth = coordinates.size.width
                }
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = AppColors.color_bg_default)
                .border(
                    border = BorderStroke(
                        1.dp,
                        AppColors.color_main_secondary
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = selectedCurrency,
                color = AppColors.color_main_text_default,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(
                    id = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ),
                contentDescription = "",
                tint = AppColors.color_main_primary
            )
        }

        if (expanded) {
            Popup(
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true)
            ) {
                Column(
                    modifier = Modifier
                        .width(with(density) { selectorWidth.toDp() })

                        .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp))
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = AppColors.color_bg_default)
                        .border(
                            border = BorderStroke(1.dp, AppColors.color_main_secondary),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clickable { expanded = !expanded }
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = selectedCurrency,
                            color = AppColors.color_main_text_default,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_up),
                            contentDescription = "",
                            tint = AppColors.color_main_primary
                        )
                    }

                    currencies.forEach { currency ->
                        CurrencyDropdownItem(
                            currency = currency,
                            isSelected = currency == selectedCurrency,
                            onClick = {
                                onCurrencySelected(currency)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrencyDropdownItem(
    currency: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .then(
                    if (isSelected) {
                        Modifier.background(AppColors.color_main_light_primary)
                    } else {
                        Modifier
                    }
                )
                .clickable { onClick() }
                .padding(start = 16.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = currency,
                color = AppColors.color_main_text_default,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}