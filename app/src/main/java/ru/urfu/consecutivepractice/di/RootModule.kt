package ru.urfu.consecutivepractice.di

import androidx.datastore.core.DataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.urfu.consecutivepractice.data.dataSource.DataSourceProvider
import ru.urfu.consecutivepractice.data.mapper.FormulaOneResponseToEntityMapper
import ru.urfu.consecutivepractice.data.repository.FormulaOneRepository
import ru.urfu.consecutivepractice.data.repository.ProfileRepository
import ru.urfu.consecutivepractice.domain.model.ProfileEntity
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository
import ru.urfu.consecutivepractice.domain.repository.IProfileRepository
import ru.urfu.consecutivepractice.presentation.drivers.viewModel.DriversViewModel
import ru.urfu.consecutivepractice.presentation.drivers.viewModel.FavoriteDriversViewModel
import ru.urfu.consecutivepractice.presentation.mapper.FormulaOneUiMapper
import ru.urfu.consecutivepractice.presentation.profile.viewModel.EditProfileViewModel
import ru.urfu.consecutivepractice.presentation.profile.viewModel.ProfileViewModel
import ru.urfu.consecutivepractice.presentation.viewModel.ListViewModel

val rootModule = module {
    single<IFormulaOneRepository> { FormulaOneRepository(get(), get(), get()) }
    factory { FormulaOneUiMapper() }
    factory { FormulaOneResponseToEntityMapper() }

    factory<DataStore<ProfileEntity>>(named("profile")) { DataSourceProvider(get()).provide() }
    single<IProfileRepository> { ProfileRepository() }

    viewModel { ListViewModel(get(), get(), get()) }

    viewModel { DriversViewModel(get(), get(), it.get()) }

    viewModel { FavoriteDriversViewModel(get(), get()) }

    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get(), it.get(), androidContext()) }
}