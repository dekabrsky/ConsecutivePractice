package ru.urfu.consecutivepractice.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.urfu.consecutivepractice.data.api.FormulaOneApi
import ru.urfu.consecutivepractice.data.db.DriversDatabase
import ru.urfu.consecutivepractice.data.mapper.FormulaOneResponseToEntityMapper
import ru.urfu.consecutivepractice.data.model.DriverDbEntity
import ru.urfu.consecutivepractice.domain.model.ConstructorEntity
import ru.urfu.consecutivepractice.domain.model.DriverEntity
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository

class FormulaOneRepository(
    private val api: FormulaOneApi,
    private val driverDb: DriversDatabase,
    private val mapper: FormulaOneResponseToEntityMapper
): IFormulaOneRepository {
    override suspend fun getConstructors(year: Int): List<ConstructorEntity> {
        return withContext(Dispatchers.IO) {
            mapper.mapConstructors(api.getConstructors(year))

            /*listOf(
                ConstructorEntity(1L, "Red Bull", BigDecimal.TEN),
                ConstructorEntity(2L, "Mercedes", BigDecimal.TEN),
                ConstructorEntity(3L, "McLaren", BigDecimal.TEN),
                ConstructorEntity(4L, "Ferrari", BigDecimal.TEN)
            )*/
        }
    }

    override suspend fun getDrivers(lastId: String?): List<DriverEntity> {
        return withContext(Dispatchers.IO) {
            mapper.mapDrivers(api.getDrivers(lastId))

            /*listOf(
                DriverEntity("Carlos Sainz", "SAI", "ES"),
                DriverEntity("Lando Norris", "NOR", "GB"),
                DriverEntity("Charles Leclerc", "LEC", "MC"),
                DriverEntity("Max Verstappen", "VER", "NL"),
            )*/
        }
    }

    override suspend fun saveDriver(driver: DriverEntity) {
        return withContext(Dispatchers.IO) {
            driverDb.driversDao().insert(
                DriverDbEntity(
                    name = driver.name,
                    tag = driver.tag,
                    nationality = driver.nationality
                )
            )
        }
    }

    override suspend fun getSavedDrivers(): List<DriverEntity> {
        return withContext(Dispatchers.IO) {
            driverDb.driversDao().getAll().map {
                DriverEntity(
                    it.id?.toString().orEmpty(),
                    it.name.orEmpty(),
                    it.tag.orEmpty(),
                    it.nationality.orEmpty()
                )
            }
        }
    }
}