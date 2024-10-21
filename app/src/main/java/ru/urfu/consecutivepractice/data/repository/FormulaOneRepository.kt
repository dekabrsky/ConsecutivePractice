package ru.urfu.consecutivepractice.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.urfu.consecutivepractice.data.api.FormulaOneApi
import ru.urfu.consecutivepractice.data.mapper.FormulaOneResponseToEntityMapper
import ru.urfu.consecutivepractice.domain.model.ConstructorEntity

class FormulaOneRepository(
    private val api: FormulaOneApi,
    private val mapper: FormulaOneResponseToEntityMapper
) {
    suspend fun getConstructors(year: Int): List<ConstructorEntity> {
        return withContext(Dispatchers.IO) {
            mapper.mapConstructors(api.getConstructors(year))
        }
    }
}