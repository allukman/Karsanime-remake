package com.karsatech.karsanime.core.di

import com.karsatech.karsanime.core.domain.repository.anime.AnimeRepository
import com.karsatech.karsanime.core.domain.repository.anime.IAnimeRepository
import com.karsatech.karsanime.core.domain.repository.manga.IMangaRepository
import com.karsatech.karsanime.core.domain.repository.manga.MangaRepository
import com.karsatech.karsanime.core.domain.repository.people.IPeopleRepository
import com.karsatech.karsanime.core.domain.repository.people.PeopleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepositoryAnime(animeRepository: AnimeRepository): IAnimeRepository

    @Binds
    abstract fun provideRepositoryManga(mangaRepository: MangaRepository): IMangaRepository

    @Binds
    abstract fun provideRepositoryPeople(peopleRepository: PeopleRepository): IPeopleRepository
}