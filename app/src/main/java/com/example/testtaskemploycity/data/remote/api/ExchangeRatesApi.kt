package com.example.testtaskemploycity.data.remote.api

import com.example.testtaskemploycity.data.remote.dto.LatestRatesDto
import retrofit2.http.GET
import retrofit2.http.Query


interface ExchangeRatesApi {

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): LatestRatesDto

}
