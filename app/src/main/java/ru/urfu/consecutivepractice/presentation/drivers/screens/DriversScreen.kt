package ru.urfu.consecutivepractice.presentation.drivers.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import ru.urfu.consecutivepractice.R
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel
import ru.urfu.consecutivepractice.presentation.drivers.viewModel.DriversViewModel
import ru.urfu.consecutivepractice.ui.components.FullScreenProgress
import ru.urfu.consecutivepractice.ui.theme.Typography

@Parcelize
class DriversScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Suppress("MagicNumber")
    @Composable
    override fun Content(modifier: Modifier) {
        val viewModel = koinViewModel<DriversViewModel>()
        val state = viewModel.viewState

        Column(Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.drivers))
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                    )
                },
                modifier = Modifier.shadow(elevation=1.dp)
            )

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
                    DriverItem(item = it)
                }
            }
        }

        if (state.loading) {
            FullScreenProgress()
        }
    }
}



@Composable
private fun DriverItem(
    item: DriverUiModel
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = item.tag, style = Typography.titleMedium)
        Text(
            text = item.name,
            style = Typography.titleMedium,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        AsyncImage(
            model = item.flagUrl,
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DriverItemPreview() {
    DriverItem(item = DriverUiModel("Lewis Hamilton", "HAM", ""))
}

