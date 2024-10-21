package ru.urfu.consecutivepractice

import android.app.Application
import com.github.terrakok.modo.ModoDevOptions
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import logcat.logcat
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.urfu.consecutivepractice.di.networkModule
import ru.urfu.consecutivepractice.di.rootModule

class ConsecutivePracticeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
        ModoDevOptions.onIllegalScreenModelStoreAccess = ModoDevOptions.ValidationFailedStrategy { throwable ->
            throw throwable
        }
        ModoDevOptions.onIllegalClearState = ModoDevOptions.ValidationFailedStrategy { throwable ->
            logcat(priority = LogPriority.ERROR) { "Cleaning state of composable, which still can be visible for user." }
        }

        startKoin {
            androidLogger()
            androidContext(this@ConsecutivePracticeApp)
            modules(rootModule, networkModule)
        }
    }
}