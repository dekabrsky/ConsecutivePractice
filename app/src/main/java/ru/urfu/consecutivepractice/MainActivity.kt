package ru.urfu.consecutivepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationManagerCompat
import com.github.terrakok.modo.Modo.rememberRootScreen
import com.github.terrakok.modo.RootScreen
import com.github.terrakok.modo.stack.DefaultStackScreen
import com.github.terrakok.modo.stack.StackNavModel
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import ru.urfu.consecutivepractice.di.rootModule
import ru.urfu.consecutivepractice.presentation.profile.channelManager.NotificationChannelManager
import ru.urfu.consecutivepractice.presentation.screens.MainTabScreenFinal
import ru.urfu.consecutivepractice.ui.theme.ConsecutivePracticeTheme

class MainActivity : ComponentActivity() {

    private val channelManager: NotificationChannelManager by lazy {
        NotificationChannelManager(NotificationManagerCompat.from(this), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        channelManager.createNotificationChannels()
        setContent {
            val rootScreen: RootScreen<DefaultStackScreen> = rememberRootScreen {
                DefaultStackScreen(
                    StackNavModel(
                        MainTabScreenFinal()
                    )
                )
            }

            ConsecutivePracticeTheme {
                Surface(color = Color.White) {
                    rootScreen.Content(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

