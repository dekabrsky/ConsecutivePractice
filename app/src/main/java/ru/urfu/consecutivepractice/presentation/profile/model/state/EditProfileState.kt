package ru.urfu.consecutivepractice.presentation.profile.model.state

import android.net.Uri
import org.threeten.bp.LocalTime

interface EditProfileState {
    val photoUri: Uri
    val name: String
    val url: String
    val time: LocalTime
    val timeString: String
    val timeError: String?
    val isNeedToShowPermission: Boolean
    val isNeedToShowSelect: Boolean
    val isNeedToShowTimePicker: Boolean
}