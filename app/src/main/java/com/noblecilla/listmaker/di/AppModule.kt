package com.noblecilla.listmaker.di

import com.noblecilla.listmaker.model.ListDataSource
import com.noblecilla.listmaker.model.ListRepository
import com.noblecilla.listmaker.viewmodel.ListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ListDataSource> { ListRepository(androidContext()) }
    viewModel { ListViewModel(get()) }
}
