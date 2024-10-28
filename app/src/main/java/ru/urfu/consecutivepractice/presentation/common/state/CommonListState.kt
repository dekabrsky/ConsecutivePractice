package ru.urfu.consecutivepractice.presentation.common.state

import androidx.compose.runtime.Stable

@Stable
interface CommonListState {
    val error: String?
    val loading: Boolean
}