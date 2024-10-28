package ru.urfu.consecutivepractice.data.model

import androidx.annotation.Keep

@Keep
class DriversPagingResponse(
    val drivers: List<DriverResponse?>?,
    val total: Long?
)
