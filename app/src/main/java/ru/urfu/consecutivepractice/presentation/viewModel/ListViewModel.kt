package ru.urfu.consecutivepractice.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.urfu.consecutivepractice.coroutinesUtils.launchLoadingAndError
import ru.urfu.consecutivepractice.data.repository.FormulaOneRepository
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository
import ru.urfu.consecutivepractice.presentation.mapper.FormulaOneUiMapper
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel
import ru.urfu.consecutivepractice.presentation.state.ListState

class ListViewModel(
    private val repository: IFormulaOneRepository,
    private val mapper: FormulaOneUiMapper
) : ViewModel() {

    private val mutableState = MutableListState()
    val viewState = mutableState as ListState

    init {
        loadConstructors()
    }

    private fun loadConstructors() {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.loading = it }
        ) {
            mutableState.items = emptyList()
            mutableState.error = null

            mutableState.items =
                mapper.mapConstructors(repository.getConstructors(viewState.year))
        }
    }

    fun setYear(year: Int) {
        mutableState.year = year
        loadConstructors()
        onDialogClose()
    }

    fun onYearClicked() {
        mutableState.isYearDialogVisible = true
    }

    fun onDialogClose() {
        mutableState.isYearDialogVisible = false
    }

    fun onReloadClicked() {
        loadConstructors()
    }

    private class MutableListState : ListState {
        override var items: List<ConstructorUiModel> by mutableStateOf(emptyList())
        override var isYearDialogVisible: Boolean by mutableStateOf(false)
        override var year: Int by mutableIntStateOf(END_YEAR)
        override var years: List<Int> by mutableStateOf((START_YEAR .. END_YEAR).toList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }

    companion object {
        private const val START_YEAR = 1950
        private const val END_YEAR = 2024
    }
}
