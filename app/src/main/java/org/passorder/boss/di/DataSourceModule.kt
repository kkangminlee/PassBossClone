package org.passorder.boss.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.passorder.data.datasource.AuthDataSource
import org.passorder.data.datasource.OrderDataSource
import org.passorder.data.datasource.remote.AuthDataSourceImpl
import org.passorder.data.datasource.remote.OrderDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideAuthDataSource(authDataSource: AuthDataSourceImpl): AuthDataSource = authDataSource

    @Singleton
    @Provides
    fun provideOrderDataSource(orderDataSource: OrderDataSourceImpl): OrderDataSource = orderDataSource
}