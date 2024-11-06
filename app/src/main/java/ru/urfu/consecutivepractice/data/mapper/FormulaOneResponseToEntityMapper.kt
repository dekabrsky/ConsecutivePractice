package ru.urfu.consecutivepractice.data.mapper

import ru.urfu.consecutivepractice.data.model.ConstructorsPagingResponse
import ru.urfu.consecutivepractice.data.model.DriversPagingResponse
import ru.urfu.consecutivepractice.domain.model.ConstructorEntity
import ru.urfu.consecutivepractice.domain.model.DriverEntity
import java.math.BigDecimal
import java.util.ListResourceBundle

class FormulaOneResponseToEntityMapper {
    fun mapConstructors(response: ConstructorsPagingResponse): List<ConstructorEntity> {
        return response.constructors?.map {
            ConstructorEntity(
                position = it?.position ?: 0L,
                name = it?.name.orEmpty(),
                points = it?.points ?: BigDecimal.ZERO
            )
        }.orEmpty()
    }

    fun mapDrivers(response: DriversPagingResponse): List<DriverEntity> {
        return response.drivers?.map {
            DriverEntity(
                id = it?.id.orEmpty(),
                tag = it?.tag.orEmpty(),
                name = it?.name.orEmpty(),
                nationality = it?.nationality.orEmpty()
            )
        }.orEmpty()
    }
}