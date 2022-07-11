package org.passorder.boss.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.passorder.data.repository.AuthRepositoryImpl
import org.passorder.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository = authRepository
}