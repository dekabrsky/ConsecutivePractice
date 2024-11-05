package ru.urfu.consecutivepractice.presentation.drivers.state

import androidx.compose.runtime.Stable
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel

@Stable
interface FavoriteDriversState {
    val items: List<DriverUiModel>
}