package ru.urfu.consecutivepractice.domain.repository

import ru.urfu.consecutivepractice.domain.model.ConstructorEntity

interface IFormulaOneRepository {
    suspend fun getConstructors(year: Int): List<ConstructorEntity>
}