package ru.urfu.consecutivepractice.presentation.mapper

import androidx.compose.ui.graphics.Color
import ru.urfu.consecutivepractice.domain.model.ConstructorEntity
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel
import ru.urfu.consecutivepractice.ui.theme.Bronze

class FormulaOneUiMapper {
    fun mapConstructors(entity: ConstructorEntity): ConstructorUiModel {
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
}