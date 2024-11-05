package ru.urfu.consecutivepractice.presentation.drivers.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.forward
import kotlinx.coroutines.launch
import ru.urfu.consecutivepractice.coroutinesUtils.launchLoadingAndError
import ru.urfu.consecutivepractice.domain.model.DriverEntity
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel
import ru.urfu.consecutivepractice.presentation.drivers.screens.FavoriteDriversScreen
import ru.urfu.consecutivepractice.presentation.drivers.state.DriversState
import ru.urfu.consecutivepractice.presentation.mapper.FormulaOneUiMapper

class DriversViewModel(
    private val repository: IFormulaOneRepository,
    private val uiMapper: FormulaOneUiMapper,
    private val navigation: StackNavContainer
) : ViewModel() {

    private var drivers: List<DriverEntity> = emptyList()

    private val mutableState = MutableDriversState()
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

            drivers = repository.getDrivers()
            mutableState.items = drivers.map { uiMapper.mapDriver(it) }
        }
    }

    fun onFavoritesClicked() {
        navigation.forward(FavoriteDriversScreen())
    }

    fun onReloadClicked() {
        loadDrivers()
    }

    fun onFavoriteClicked(driver: DriverUiModel) {
        viewModelScope.launch {
            drivers.find { it.name == driver.name }?.let { repository.saveDriver(it) }
        }
    }

    private class MutableDriversState : DriversState {
        override var items: List<DriverUiModel> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }
}
