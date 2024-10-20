package ru.urfu.consecutivepractice.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class ConstructorUiModel(
    val position: String,
    val name: String,
    val points: String
)