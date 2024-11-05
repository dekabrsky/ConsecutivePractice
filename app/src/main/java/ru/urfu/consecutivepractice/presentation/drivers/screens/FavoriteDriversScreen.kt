package ru.urfu.consecutivepractice.presentation.drivers.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import ru.urfu.consecutivepractice.presentation.drivers.viewModel.FavoriteDriversViewModel

@Parcelize
class FavoriteDriversScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {
    @Composable
    override fun Content(modifier: Modifier) {
        val viewModel = koinViewModel<FavoriteDriversViewModel>()
        val state = viewModel.viewState

        val lazyColumnState = rememberSaveable(saver = LazyListState.Saver) {
            LazyListState(
                0,
                0
            )
        }

        Scaffold {
            LazyColumn(
                Modifier.fillMaxSize().padding(it),
                lazyColumnState
            ) {
                items(state.items) {
                    DriverItem(item = it, viewModel::onFavoriteClicked)
                }
            }
        }
    }
}