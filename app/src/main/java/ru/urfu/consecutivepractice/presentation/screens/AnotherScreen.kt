package ru.urfu.consecutivepractice.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import kotlinx.parcelize.Parcelize

@Parcelize
class AnotherScreen(
    override val screenKey: ScreenKey = generateScreenKey()
): Screen {
    @Composable
    override fun Content(modifier: Modifier) {

    }
}