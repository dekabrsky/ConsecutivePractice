package ru.urfu.consecutivepractice.presentation.mapper

import androidx.compose.ui.graphics.Color
import ru.urfu.consecutivepractice.domain.model.ConstructorEntity
import ru.urfu.consecutivepractice.domain.model.DriverEntity
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel
import ru.urfu.consecutivepractice.ui.theme.Bronze

class FormulaOneUiMapper {
    fun mapConstructors(list: List<ConstructorEntity>): List<ConstructorUiModel> =
        list.sortedBy { it.position }.map { mapConstructor(it) }

    private fun mapConstructor(entity: ConstructorEntity): ConstructorUiModel {
        return ConstructorUiModel(
            entity.position.toString(),
            entity.name,
            entity.points.toPlainString(),
            positionColor = when (entity.position) {
                1L -> Color.Yellow
                2L -> Color.Gray
                3L -> Bronze
                else -> Color.Unspecified
            }
        )
    }

    fun mapDriver(driver: DriverEntity): DriverUiModel {
        return DriverUiModel(
            tag = driver.tag,
            name = driver.name,
            flagUrl = "https://flagsapi.com/${driver.nationality.take(2)}/flat/64.png"
        )
    }
}