package ru.urfu.consecutivepractice.presentation.drivers.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.urfu.consecutivepractice.coroutinesUtils.launchLoadingAndError
import ru.urfu.consecutivepractice.domain.model.DriverEntity
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel
import ru.urfu.consecutivepractice.presentation.drivers.state.DriversState
import ru.urfu.consecutivepractice.presentation.mapper.FormulaOneUiMapper
import ru.urfu.consecutivepractice.presentation.state.ListState

class DriversViewModel(
    private val repository: IFormulaOneRepository,
    private val uiMapper: FormulaOneUiMapper
) : ViewModel() {

    private val mutableState = MutableListState()
    val viewState = mutableState as DriversState

    init {
        loadDrivers()
    }

    private fun loadDrivers() {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.loading = it }
        ) {
            mutableState.items = emptyList()
            mutableState.error = null

            mutableState.items = repository.getDrivers().map { uiMapper.mapDriver(it) }
        }
    }

    fun onReloadClicked() {
        loadDrivers()
    }

    private class MutableListState : DriversState {
        override var items: List<DriverUiModel> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }
}
