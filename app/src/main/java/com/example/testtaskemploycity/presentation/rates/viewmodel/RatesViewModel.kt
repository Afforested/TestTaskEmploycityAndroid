package com.example.testtaskemploycity.presentation.rates.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskemploycity.di.IODispatcher
import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.usecase.AddToFavoritesUseCase
import com.example.testtaskemploycity.domain.usecase.GetFavoriteCurrenciesUseCase
import com.example.testtaskemploycity.domain.usecase.GetLatestRatesUseCase
import com.example.testtaskemploycity.domain.usecase.RemoveFromFavoritesUseCase
import com.example.testtaskemploycity.domain.usecase.SortCurrencyRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RatesViewModel @Inject constructor(
    private val getLatestRatesUseCase: GetLatestRatesUseCase,
    private val getFavoriteCurrenciesUseCase: GetFavoriteCurrenciesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val sortCurrencyRatesUseCase: SortCurrencyRatesUseCase,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(RatesState())
    val state: StateFlow<RatesState> = _state.asStateFlow()

    init {
        loadRates()
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch(ioDispatcher) {
            getFavoriteCurrenciesUseCase().collect { favorites ->
                val favoriteCodes = favorites
                    .filter { it.baseCurrency == _state.value.baseCurrency }
                    .map { it.currency.code }
                    .toSet()

                _state.update { currentState ->
                    currentState.copy(
                        rates = currentState.rates.map { rate ->
                            rate.copy(isFavorite = rate.currency.code in favoriteCodes)
                        }
                    )
                }
            }
        }
    }



    fun onEvent(event: RatesEvent) {
        when (event) {
            is RatesEvent.ChangeBaseCurrency -> {
                _state.update { it.copy(baseCurrency = event.currency) }
                loadRates()
            }

            is RatesEvent.ChangeSortType -> {
                _state.update { currentState ->
                    currentState.copy(
                        sortType = event.sortType,
                        rates = sortCurrencyRatesUseCase(currentState.rates, event.sortType)
                    )
                }
            }

            is RatesEvent.ToggleFavorite -> toggleFavorite(event.currencyRate)

            RatesEvent.Refresh -> refreshRates()

        }
    }


    private fun loadRates() {
        viewModelScope.launch(ioDispatcher) {
            _state.update { it.copy(isLoading = true, error = null) }

            getLatestRatesUseCase(
                baseCurrency = _state.value.baseCurrency,
                sortType = _state.value.sortType
            ).fold(
                onSuccess = { rates ->
                    _state.update { currentState ->
                        currentState.copy(
                            rates = rates,
                            isLoading = false,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Unknown error"
                        )
                    }
                }
            )
        }
    }

    private fun refreshRates() {
        viewModelScope.launch(ioDispatcher) {
            _state.update { it.copy(isRefreshing = true) }

            getLatestRatesUseCase(
                baseCurrency = _state.value.baseCurrency,
                sortType = _state.value.sortType
            ).fold(
                onSuccess = { rates ->
                    _state.update { currentState ->
                        currentState.copy(
                            rates = rates,
                            isRefreshing = false,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            error = exception.message ?: "Unknown error"
                        )
                    }
                }
            )
        }
    }

    private fun toggleFavorite(currencyRate: CurrencyRate) {
        viewModelScope.launch(ioDispatcher) {
            if (currencyRate.isFavorite) {
                removeFromFavoritesUseCase(
                    currencyRate.currency.code,
                    _state.value.baseCurrency
                )
            } else {
                addToFavoritesUseCase(
                    currencyRate.currency.code,
                    _state.value.baseCurrency,
                    currencyRate.rate
                )
            }

        }
    }
}