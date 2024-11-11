package ru.urfu.consecutivepractice.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.urfu.consecutivepractice.domain.model.ProfileEntity
import ru.urfu.consecutivepractice.domain.model.ProfileEntityKt

interface IProfileRepository {
    suspend fun getProfile(): ProfileEntity?
    suspend fun setProfile(photoUri: String, name: String, url: String): ProfileEntity
    suspend fun observeProfile(): Flow<ProfileEntity>
}