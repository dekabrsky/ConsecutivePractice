package ru.urfu.consecutivepractice.presentation.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.urfu.consecutivepractice.coroutinesUtils.launchLoadingAndError
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository
import ru.urfu.consecutivepractice.presentation.mapper.FormulaOneUiMapper
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel
import ru.urfu.consecutivepractice.presentation.state.ListState

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ListViewModel(
    private val repository: IFormulaOneRepository,
    private val mapper: FormulaOneUiMapper,
    private val context: Context
) : ViewModel() {

    val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
    val exampleCounterFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[EXAMPLE_COUNTER] ?: 0
        }

    private val mutableState = MutableListState()
    val viewState = mutableState as ListState

    init {
        loadConstructors()

        viewModelScope.launch {
            exampleCounterFlow.collect {
                setYear(it)
            }
        }
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

    private fun setYear(year: Int) {
        mutableState.year = year
        loadConstructors()
    }

    fun updateYear(year: Int) {
        viewModelScope.launch {
            saveYear(year)
        }
        onDialogClose()
        setYear(year)
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

    private suspend fun saveYear(year: Int) {
        context.dataStore.edit { settings ->
            settings[EXAMPLE_COUNTER] = year
        }
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
