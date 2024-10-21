package ru.urfu.consecutivepractice.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.urfu.consecutivepractice.data.mapper.FormulaOneResponseToEntityMapper
import ru.urfu.consecutivepractice.data.repository.FormulaOneRepository
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository
import ru.urfu.consecutivepractice.presentation.mapper.FormulaOneUiMapper
import ru.urfu.consecutivepractice.presentation.viewModel.ListViewModel

val rootModule = module {
    single<IFormulaOneRepository> { FormulaOneRepository(get(), get()) }
    factory { FormulaOneUiMapper() }
    factory { FormulaOneResponseToEntityMapper() }
    viewModel { ListViewModel(get(), get()) }
}