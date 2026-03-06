package com.lucasdev.jornadacerta.di

import com.lucasdev.jornadacerta.common.data.local.datasource.LocalDataSource
import com.lucasdev.jornadacerta.common.data.local.datasource.LocalDataSourceImpl
import com.lucasdev.jornadacerta.common.data.notification.AndroidNotificationHelper
import com.lucasdev.jornadacerta.common.data.notification.NotificationHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    @Binds
    fun bindNotificationHelper(impl: AndroidNotificationHelper): NotificationHelper

}