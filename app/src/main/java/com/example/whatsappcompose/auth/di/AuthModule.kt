package com.example.whatsappcompose.auth.di

import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.example.whatsappcompose.auth.domain.use_cases.LoginUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesLoginUseCase(authRepository: AuthRepository) = LoginUseCase(authRepository)
}