package com.karsatech.karsanime.di

import com.karsatech.karsanime.core.domain.usecase.anime.AnimeInteractor
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import com.karsatech.karsanime.core.domain.usecase.manga.MangaInteractor
import com.karsatech.karsanime.core.domain.usecase.manga.MangaUseCase
import com.karsatech.karsanime.core.domain.usecase.people.PeopleInteractor
import com.karsatech.karsanime.core.domain.usecase.people.PeopleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideAnimeUseCase(animeInteractor: AnimeInteractor): AnimeUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideMangaUseCase(mangaInteractor: MangaInteractor): MangaUseCase

    @Binds
    @ViewModelScoped
    abstract fun providePeopleUseCase(peopleInteractor: PeopleInteractor): PeopleUseCase

}
