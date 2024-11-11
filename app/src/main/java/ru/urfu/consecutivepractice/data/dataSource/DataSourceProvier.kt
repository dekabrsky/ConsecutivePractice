package ru.urfu.consecutivepractice.data.dataSource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import ru.urfu.consecutivepractice.data.serializer.ProfileSerializer
import ru.urfu.consecutivepractice.domain.model.ProfileEntity
import ru.urfu.consecutivepractice.presentation.viewModel.dataStore

class DataSourceProvider(val context: Context) {
    private val Context.profileDataStore: DataStore<ProfileEntity> by dataStore(
        fileName = "profile.pb",
        serializer = ProfileSerializer
    )

    fun provide() = context.profileDataStore
}