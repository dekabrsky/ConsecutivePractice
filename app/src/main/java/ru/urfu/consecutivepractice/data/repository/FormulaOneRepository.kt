package ru.urfu.consecutivepractice.data.repository

import ru.urfu.consecutivepractice.domain.model.ConstructorEntity
import java.math.BigDecimal

class FormulaOneRepository {
    fun getConstructors(): List<ConstructorEntity> {
        return listOf(
            ConstructorEntity(1L, "RedBull", BigDecimal.valueOf(860)),
            ConstructorEntity(2L, "Mercedes", BigDecimal.valueOf(409))
        )
    }
}