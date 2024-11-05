package ru.urfu.consecutivepractice.presentation.drivers.model

import androidx.compose.runtime.Immutable

@Immutable
class DriverUiModel(
    val tag: String,
    val name: String,
    val flagUrl: String
)