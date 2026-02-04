package com.example.testtaskemploycity.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.testtaskemploycity.R
import com.example.testtaskemploycity.presentation.common.CurrencyRateItem
import com.example.testtaskemploycity.presentation.common.OutlineDivider
import com.example.testtaskemploycity.presentation.common.TopBarTitle
import com.example.testtaskemploycity.presentation.favorites.viewmodel.FavoritesEvent
import com.example.testtaskemploycity.presentation.favorites.viewmodel.FavoritesViewModel
import com.example.testtaskemploycity.presentation.ui.theme.AppColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
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

            TopBarTitle(
                title = stringResource(R.string.favorites),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
            )
            OutlineDivider()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            if (state.favorites.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.favorites) { currencyRate ->
                        CurrencyRateItem(
                            currencyRate = currencyRate,
                            onFavoriteClick = { rate ->
                                viewModel.onEvent(FavoritesEvent.RemoveFavorite(rate))
                            },
                            showPair = true
                        )
                    }
                }
            }
        }
    }
}