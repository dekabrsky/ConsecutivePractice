package ru.urfu.consecutivepractice.presentation.state

import androidx.compose.runtime.Stable
import ru.urfu.consecutivepractice.domain.model.ConstructorEntity
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel

@Stable
interface ListState {
    val items: List<ConstructorUiModel>
}