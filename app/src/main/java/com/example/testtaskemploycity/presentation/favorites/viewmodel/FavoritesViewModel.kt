package com.example.testtaskemploycity.presentation.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskemploycity.di.IODispatcher
import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.usecase.GetFavoriteCurrenciesUseCase
import com.example.testtaskemploycity.domain.usecase.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteCurrenciesUseCase: GetFavoriteCurrenciesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        loadFavorites()
    }

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.RemoveFavorite -> removeFavorite(event.currencyRate)
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch(ioDispatcher) {
            getFavoriteCurrenciesUseCase().collect { favorites ->
                _state.value = FavoritesState(favorites = favorites)
            }
        }
    }

    private fun removeFavorite(currencyRate: CurrencyRate) {
        viewModelScope.launch(ioDispatcher) {
            removeFromFavoritesUseCase(
                currencyRate.currency.code,
                currencyRate.baseCurrency ?: "USD"
            )
        }
    }
}
