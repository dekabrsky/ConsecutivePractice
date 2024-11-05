package ru.urfu.consecutivepractice.presentation.drivers.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.urfu.consecutivepractice.domain.model.DriverEntity
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel
import ru.urfu.consecutivepractice.presentation.drivers.state.FavoriteDriversState
import ru.urfu.consecutivepractice.presentation.mapper.FormulaOneUiMapper

class FavoriteDriversViewModel(
    private val repository: IFormulaOneRepository,
    private val uiMapper: FormulaOneUiMapper
) : ViewModel() {

    private var drivers: List<DriverEntity> = emptyList()

    private val mutableState = MutableDriversState()
    val viewState = mutableState as FavoriteDriversState

    init {
        loadDrivers()
    }

    private fun loadDrivers() {
        viewModelScope.launch {
            drivers = repository.getSavedDrivers()
            mutableState.items = drivers.map { uiMapper.mapDriver(it) }
        }
    }

    fun onFavoriteClicked(driver: DriverUiModel) {
        viewModelScope.launch {
            drivers.find { it.name == driver.name }?.let { repository.saveDriver(it) }
        }
    }

    private class MutableDriversState : FavoriteDriversState {
        override var items: List<DriverUiModel> by mutableStateOf(emptyList())
    }
}