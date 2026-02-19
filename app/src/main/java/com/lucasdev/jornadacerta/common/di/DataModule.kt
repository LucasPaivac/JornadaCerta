package com.lucasdev.jornadacerta.common.di

import com.lucasdev.jornadacerta.common.data.local.datasource.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSourceImpl

}