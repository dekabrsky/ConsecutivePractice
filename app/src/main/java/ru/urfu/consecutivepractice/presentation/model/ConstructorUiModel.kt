package ru.urfu.consecutivepractice.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class ConstructorUiModel(
    val position: String,
    val name: String,
    val points: String,
    @IgnoredOnParcel val positionColor: Color = Color.Unspecified
): Parcelable