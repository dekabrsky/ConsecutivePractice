package ru.urfu.consecutivepractice.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import org.threeten.bp.LocalTime
import ru.urfu.consecutivepractice.domain.model.ProfileEntity
import ru.urfu.consecutivepractice.domain.repository.IProfileRepository

class ProfileRepository: IProfileRepository {
    private val dataStore : DataStore<ProfileEntity> by inject(DataStore::class.java, named("profile"))

    override suspend fun observeProfile(): Flow<ProfileEntity> = dataStore.data

    override suspend fun getProfile(): ProfileEntity? = dataStore.data.firstOrNull()

    override suspend fun setProfile(photoUri: String, name: String, url: String, time: LocalTime) =
        dataStore.updateData {
            it.toBuilder().apply {
                this.photoUri = photoUri
                this.name = name
                this.url = url
                this.time = time.toString()
            }.build()
        }
}