package ru.urfu.consecutivepractice.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.forward
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker
import com.sd.lib.compose.wheel_picker.FWheelPickerState
import com.sd.lib.compose.wheel_picker.rememberFWheelPickerState
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import ru.urfu.consecutivepractice.R
import ru.urfu.consecutivepractice.presentation.model.ConstructorUiModel
import ru.urfu.consecutivepractice.presentation.state.ListState
import ru.urfu.consecutivepractice.presentation.viewModel.ListViewModel
import ru.urfu.consecutivepractice.ui.components.FullScreenProgress
import ru.urfu.consecutivepractice.ui.theme.Typography

@Parcelize
class ListScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Suppress("MagicNumber")
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current

        val viewModel = koinViewModel<ListViewModel>()
        val state = viewModel.viewState

        val pickerState = rememberFWheelPickerState(state.currentYearPosition)

        Column(Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text(
                    text = state.year.toString(),
                    Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onYearClicked() })
            })

            val lazyColumnState = rememberSaveable(saver = LazyListState.Saver) {
                LazyListState(
                    0,
                    0
                )
            }

            state.error?.let {
                Text(text = it)
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    Modifier.clickable { viewModel.onReloadClicked() }
                )
            }

            LazyColumn(
                Modifier.fillMaxSize(),
                lazyColumnState
            ) {
                items(state.items) {
                    ConstructorItem(item = it, navigation)
                }
            }
        }

        if (state.isYearDialogVisible) {
            YearDialog(viewModel, pickerState, state)
        }

        if (state.loading) {
            FullScreenProgress()
        }
    }

    @Composable
    private fun YearDialog(
        viewModel: ListViewModel,
        pickerState: FWheelPickerState,
        state: ListState,
    ) {
        AlertDialog(
            onDismissRequest = { viewModel.onDialogClose() },
            title = { Text(stringResource(R.string.select_year)) },
            text = {
                FVerticalWheelPicker(
                    state = pickerState,
                    count = state.years.size
                ) { index: Int ->
                    Text(state.years[index].toString())
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val currentIndex = pickerState.currentIndex
                        if (currentIndex != -1) {
                            viewModel.updateYear(state.years[pickerState.currentIndexSnapshot])
                        }
                    }
                ) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.onDialogClose() }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
private fun ConstructorItem(
    item: ConstructorUiModel,
    navigation: StackNavContainer? = null,
) {
    Row(Modifier
        .fillMaxWidth()
        .clickable { navigation?.forward(DetailsScreen(item)) }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(item.positionColor, CircleShape)
                .border(1.dp, Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.position, style = Typography.titleMedium)
        }
        Text(
            text = item.name,
            style = Typography.titleMedium,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Text(text = item.points, style = Typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun ConstructorItemPreview() {
    ConstructorItem(item = ConstructorUiModel("1", "Red Bull Racing", "134", Color.Yellow))
}

