package ru.urfu.consecutivepractice.domain.model

import java.math.BigDecimal

data class ConstructorEntity(
    val position: Long,
    val name: String,
    val points: BigDecimal
)