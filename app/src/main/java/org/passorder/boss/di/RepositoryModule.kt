package org.passorder.boss.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.passorder.data.repository.AuthRepositoryImpl
import org.passorder.data.repository.NoticeRepositoryImpl
import org.passorder.data.repository.OrderRepositoryImpl
import org.passorder.domain.repository.AuthRepository
import org.passorder.domain.repository.NoticeRepository
import org.passorder.domain.repository.OrderRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository = authRepository

    @Singleton
    @Provides
    fun provideOrderRepository(orderRepository: OrderRepositoryImpl): OrderRepository = orderRepository

    @Singleton
    @Provides
    fun provideNoticeRepository(noticeRepository: NoticeRepositoryImpl): NoticeRepository = noticeRepository
}