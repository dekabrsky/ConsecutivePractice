package ru.urfu.consecutivepractice.presentation.drivers.state

import androidx.compose.runtime.Stable
import ru.urfu.consecutivepractice.domain.model.DriverEntity
import ru.urfu.consecutivepractice.presentation.common.state.CommonListState
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel

@Stable
interface DriversState: CommonListState {
    val items: List<DriverUiModel>
}