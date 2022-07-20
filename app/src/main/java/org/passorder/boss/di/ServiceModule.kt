package org.passorder.boss.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.passorder.data.service.AuthService
import org.passorder.data.service.NoticeService
import org.passorder.data.service.OrderService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideOrderService(retrofit: Retrofit): OrderService =
        retrofit.create(OrderService::class.java)

    @Provides
    @Singleton
    fun provideNoticeService(retrofit: Retrofit): NoticeService =
        retrofit.create(NoticeService::class.java)
}