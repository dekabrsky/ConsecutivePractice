package ru.urfu.consecutivepractice.presentation.state

import androidx.compose.runtime.Stable
import ru.urfu.consecutivepractice.domain.model.ConstructorEntity
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel

@Stable
interface ListState {
    val items: List<ConstructorUiModel>
    val isYearDialogVisible: Boolean
    val year: Int
    val years: List<Int>
    val currentYearPosition: Int get() = years.indexOf(year)
    val error: String?
    val loading: Boolean
}