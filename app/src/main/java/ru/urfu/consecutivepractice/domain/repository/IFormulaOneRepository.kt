package ru.urfu.consecutivepractice.domain.repository

import ru.urfu.consecutivepractice.domain.model.ConstructorEntity
import ru.urfu.consecutivepractice.domain.model.DriverEntity

interface IFormulaOneRepository {
    suspend fun getConstructors(year: Int): List<ConstructorEntity>
    suspend fun getDrivers(lastId: String?): List<DriverEntity>
    suspend fun saveDriver(driver: DriverEntity)
    suspend fun getSavedDrivers(): List<DriverEntity>
}