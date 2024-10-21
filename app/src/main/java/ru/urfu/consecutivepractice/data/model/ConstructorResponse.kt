package ru.urfu.consecutivepractice.data.model

import androidx.annotation.Keep
import java.math.BigDecimal

@Keep
class ConstructorResponse(
    val id: Long?,
    val name: String?,
    val points: BigDecimal?,
    val position: Long?
)