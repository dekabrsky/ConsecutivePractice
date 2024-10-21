package ru.urfu.consecutivepractice.data.model

import androidx.annotation.Keep

@Keep
data class ConstructorsPagingResponse(
    val constructors: List<ConstructorResponse?>?,
    val total: Int?
)