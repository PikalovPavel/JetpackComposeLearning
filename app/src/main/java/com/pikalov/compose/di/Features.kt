package com.pikalov.compose.di

import com.pikalov.compose.features.AuthRepository
import com.pikalov.compose.features.GalleryRepository
import com.pikalov.compose.features.ThemeRepository
import com.pikalov.compose.featuresIMPL.AuthRepositoryImpl
import com.pikalov.compose.featuresIMPL.GalleryRepositoryImpl
import com.pikalov.compose.featuresIMPL.ThemeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface GalleryFeatures {

    @Binds
    fun bindGalleryRepository(repo: GalleryRepositoryImpl): GalleryRepository

}

@Module
@InstallIn(SingletonComponent::class)
interface AuthFeatures {

    @Binds
    fun bindAuthRepository(repo: AuthRepositoryImpl): AuthRepository


}

@Module
@InstallIn(SingletonComponent::class)
interface ThemingFeatures {

    @Binds
    fun bindThemeRepository(repo: ThemeRepositoryImpl): ThemeRepository


}