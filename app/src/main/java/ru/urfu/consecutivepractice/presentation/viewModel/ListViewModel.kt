package ru.urfu.consecutivepractice.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.urfu.consecutivepractice.data.repository.FormulaOneRepository
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel
import ru.urfu.consecutivepractice.presentation.state.ListState

class ListViewModel(
    private val repository: FormulaOneRepository
) : ViewModel() {

    private val mutableState = MutableListState()
    val viewState = mutableState as ListState

    init {
        mutableState.items = repository.getConstructors().map {
            ConstructorUiModel(
                it.position.toString(),
                it.name,
                it.points.toPlainString()
            )
        }
    }
}

private class MutableListState : ListState {
    override var items: List<ConstructorUiModel> by mutableStateOf(emptyList())
}