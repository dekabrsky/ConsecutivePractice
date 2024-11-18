package ru.urfu.consecutivepractice.domain.repository

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalTime
import ru.urfu.consecutivepractice.domain.model.ProfileEntity

interface IProfileRepository {
    suspend fun getProfile(): ProfileEntity?

    suspend fun setProfile(
        photoUri: String,
        name: String,
        url: String,
        time: LocalTime,
    ): ProfileEntity

    suspend fun observeProfile(): Flow<ProfileEntity>
}