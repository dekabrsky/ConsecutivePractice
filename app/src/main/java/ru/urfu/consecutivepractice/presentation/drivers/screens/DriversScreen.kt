package ru.urfu.consecutivepractice.presentation.drivers.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.urfu.consecutivepractice.R
import ru.urfu.consecutivepractice.presentation.drivers.viewModel.DriversViewModel
import ru.urfu.consecutivepractice.ui.components.FullScreenProgress

@Parcelize
class DriversScreen(
    override val screenKey: ScreenKey = generateScreenKey(),
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current

        val viewModel = koinViewModel<DriversViewModel> {
            parametersOf(navigation)
        }

        val state = viewModel.viewState


        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.drivers))
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            Modifier
                                .padding(end = 16.dp)
                                .clickable { viewModel.onFavoritesClicked() }
                        )
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            Modifier.padding(end = 8.dp)
                        )
                    },
                    modifier = Modifier.shadow(elevation = 1.dp)
                )
            }) { padding ->

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
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
                        DriverItem(item = it, viewModel::onFavoriteClicked)
                    }
                }

                if (state.loading) {
                    FullScreenProgress()
                }
            }
        }
    }
}
