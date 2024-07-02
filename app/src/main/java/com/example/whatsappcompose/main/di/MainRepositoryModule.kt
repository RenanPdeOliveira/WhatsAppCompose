package com.example.whatsappcompose.main.di

import com.example.whatsappcompose.main.data.repository.MainRepositoryImpl
import com.example.whatsappcompose.main.data.repository.ProfileRepositoryImpl
import com.example.whatsappcompose.main.domain.repository.MainRepository
import com.example.whatsappcompose.main.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository
}