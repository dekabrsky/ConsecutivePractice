package ru.urfu.consecutivepractice.presentation.profile.viewModel

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import ru.urfu.consecutivepractice.domain.repository.IProfileRepository
import ru.urfu.consecutivepractice.presentation.profile.model.state.EditProfileState
import ru.urfu.consecutivepractice.presentation.profile.receiver.NotificationsReceiver
import ru.urfu.consecutivepractice.presentation.profile.utils.tryParse

class EditProfileViewModel(
    private val repository: IProfileRepository,
    private val navigation: StackNavContainer,
    private val context: Context
): ViewModel() {

    private val mutableState = MutableEditProfileState()
    val viewState = mutableState as EditProfileState

    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    init {
        viewModelScope.launch {
            repository.getProfile()?.let {
                mutableState.name = it.name
                mutableState.url = it.url
                mutableState.photoUri = Uri.parse(it.photoUri)
                tryParse(it.time)?.let { time ->
                    mutableState.time = time
                    updateTimeString()
                }
            }
        }
        mutableState.isNeedToShowPermission = true
    }

    fun onNameChanged(name: String) {
        mutableState.name = name
    }

    fun onUrlChanged(url: String) {
        mutableState.url = url
    }

    fun onDoneClicked() {
        validateTime()
        if (mutableState.timeError != null) return
        viewModelScope.launch {
            repository.setProfile(
                mutableState.photoUri.toString(),
                viewState.name,
                viewState.url,
                viewState.time
            )
            saveNotification()
            back()
        }
    }

    fun onImageSelected(uri: Uri?) {
        uri?.let { mutableState.photoUri = it }
    }

    fun onPermissionClosed() {
        mutableState.isNeedToShowPermission = false
    }

    fun onPermissionDenied() {
        navigation.back()
    }

    fun onAvatarClicked() {
        mutableState.isNeedToShowSelect = true
    }

    fun onSelectDismiss() {
        mutableState.isNeedToShowSelect = false
    }

    fun onTimeInputClicked() {
        mutableState.isNeedToShowTimePicker = true
    }

    fun onTimeChanged(time: String) {
        mutableState.timeString = time
        validateTime()
    }

    fun onTimeConfirmed(h: Int, m: Int) {
        mutableState.time = LocalTime.of(h, m)
        mutableState.timeError = null
        updateTimeString()
        onTimeDialogDismiss()
    }

    fun onTimeDialogDismiss() {
        mutableState.isNeedToShowTimePicker = false
    }

    private fun saveNotification() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val dateTime = LocalDateTime.of(LocalDate.now(), viewState.time)
        val timeInMillis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val notifyIntent = Intent(context, NotificationsReceiver::class.java)

        notifyIntent.putExtras(
            Bundle().apply {
//                putLong("NOTIFICATION_TIME", time)
//                putInt("NOTIFICATION_ID", notificationModel.id)
                putString("NOTIFICATION", "Пора на пару, ${viewState.name}!")
            }
        )

        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            0, //notificationModel.id,
            notifyIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                notifyPendingIntent
            )
        } catch (e: SecurityException) {
            Log.d("alarmManager", "Failed to set reminder")
        }
    }

    private fun validateTime() {
        try {
            mutableState.time = LocalTime.parse(mutableState.timeString, formatter)
            mutableState.timeError = null
        } catch (e: Exception) {
            mutableState.timeError = "Некорректный формат времени"
        }
    }

    private fun updateTimeString() {
        mutableState.timeString = formatter.format(viewState.time)
    }

    fun back() = navigation.back()

    private class MutableEditProfileState : EditProfileState {
        override var photoUri: Uri by mutableStateOf(Uri.EMPTY)
        override var name by mutableStateOf("")
        override var url by mutableStateOf("")
        override var time: LocalTime by mutableStateOf(LocalTime.now())
        override var timeString: String by mutableStateOf("")
        override var timeError: String? by mutableStateOf(null)
        override var isNeedToShowPermission by mutableStateOf(false)
        override var isNeedToShowSelect: Boolean by mutableStateOf(false)
        override var isNeedToShowTimePicker: Boolean by mutableStateOf(false)
    }
}