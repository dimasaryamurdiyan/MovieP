package com.singaludra.moviep.di

import com.singaludra.moviep.data.source.MoviesRepository
import com.singaludra.moviep.data.source.remote.IRemoteDataSource
import com.singaludra.moviep.data.source.remote.RemoteDataSource
import com.singaludra.moviep.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(movieRepository: MoviesRepository): IMovieRepository

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource
}