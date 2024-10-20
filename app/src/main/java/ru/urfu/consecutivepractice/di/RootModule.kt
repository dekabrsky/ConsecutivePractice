package ru.urfu.consecutivepractice.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.urfu.consecutivepractice.data.repository.FormulaOneRepository
import ru.urfu.consecutivepractice.presentation.viewModel.ListViewModel

val rootModule = module {
    single<FormulaOneRepository> { FormulaOneRepository() }
    viewModel { ListViewModel(get()) }
}