package com.karsatech.karsanime.core.domain.usecase.anime

import com.karsatech.karsanime.core.domain.repository.anime.AnimeRepository
import javax.inject.Inject

class AnimeInteractor @Inject constructor(private val animeRepository: AnimeRepository):
    AnimeUseCase {

    override fun getTopAnime() = animeRepository.getTopAnime()

    override fun getUpcomingAnime() = animeRepository.getUpcomingAnime()

}