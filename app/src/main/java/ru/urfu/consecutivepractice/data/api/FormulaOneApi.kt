package ru.urfu.consecutivepractice.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.urfu.consecutivepractice.data.model.ConstructorsPagingResponse
import ru.urfu.consecutivepractice.data.model.DriversPagingResponse

interface FormulaOneApi {
    @GET("championships/{year}/constructors")
    suspend fun getConstructors(@Path("year") year: Int): ConstructorsPagingResponse

    @GET("drivers")
    suspend fun getDrivers(
        @Query("last_val") lastId: String? = null,
        @Query("limit") limit: Int = 10,
    ): DriversPagingResponse
}