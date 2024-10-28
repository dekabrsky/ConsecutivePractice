package ru.urfu.consecutivepractice.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.urfu.consecutivepractice.data.model.ConstructorsPagingResponse
import ru.urfu.consecutivepractice.data.model.DriversPagingResponse

interface FormulaOneApi {
    @GET("championships/{year}/constructors")
    suspend fun getConstructors(@Path("year") year: Int): ConstructorsPagingResponse

    @GET("drivers")
    suspend fun getDrivers(): DriversPagingResponse
}