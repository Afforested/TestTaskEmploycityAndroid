package com.example.testtaskemploycity.presentation.rates

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.testtaskemploycity.R
import com.example.testtaskemploycity.presentation.common.CurrencyRateItem
import com.example.testtaskemploycity.presentation.common.OutlineDivider
import com.example.testtaskemploycity.presentation.common.TopBarTitle
import com.example.testtaskemploycity.presentation.rates.components.BaseCurrencySelector
import com.example.testtaskemploycity.presentation.rates.viewmodel.RatesEvent
import com.example.testtaskemploycity.presentation.rates.viewmodel.RatesViewModel
import com.example.testtaskemploycity.presentation.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatesScreen(
    viewModel: RatesViewModel = hiltViewModel(),
    onNavigateToFilter: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.color_bg_default)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppColors.color_bg_header)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
            ) {
                TopBarTitle(
                    title = stringResource(R.string.currencies),
                    modifier = Modifier.weight(1f)
                )
            }


            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 8.dp)
            ) {
                BaseCurrencySelector(
                    selectedCurrency = state.baseCurrency,
                    onCurrencySelected = { currency ->
                        viewModel.onEvent(RatesEvent.ChangeBaseCurrency(currency))
                    },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onNavigateToFilter,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = AppColors.color_bg_default)
                        .border(
                            border = BorderStroke(1.dp, AppColors.color_main_secondary),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "",
                        tint = AppColors.color_main_primary
                    )
                }
            }

            OutlineDivider()
        }

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { viewModel.onEvent(RatesEvent.Refresh) },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppColors.color_main_primary
                    )
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.error) + state.error,
                            color = AppColors.color_main_primary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                state.rates.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_rates_available),
                            color = AppColors.color_main_text_secondary
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.rates) { currencyRate ->
                            CurrencyRateItem(
                                currencyRate = currencyRate,
                                onFavoriteClick = { rate ->
                                    viewModel.onEvent(RatesEvent.ToggleFavorite(rate))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}