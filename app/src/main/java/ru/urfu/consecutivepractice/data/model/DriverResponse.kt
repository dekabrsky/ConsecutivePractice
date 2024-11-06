package ru.urfu.consecutivepractice.data.model

import com.google.errorprone.annotations.Keep

@Keep
class DriverResponse(
    val id: String?,
    val name: String?,
    val tag: String?,
    val nationality: String?
)