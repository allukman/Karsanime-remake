package com.karsatech.karsanime.core.domain.usecase.anime

import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.PictureAnimeResponse
import com.karsatech.karsanime.core.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeInteractor @Inject constructor(private val animeRepository: AnimeRepository) :
    AnimeUseCase {
    override fun getTopAnime() = animeRepository.getTopAnime()

    override fun getUpcomingAnime() = animeRepository.getUpcomingAnime()

    override fun getAnimeThisSeason() = animeRepository.getAnimeThisSeason()

    override fun getTopAnimePagination() = animeRepository.getTopAnimePagination()

    override fun getUpcomingAnimePagination() = animeRepository.getUpcomingAnimePagination()

    override fun getThisSeasonAnimePagination() = animeRepository.getAnimeThisSeasonPagination()

    override fun getSearchAnimePagination(query: String) = animeRepository.getSearchAnimePagination(query)

    override fun getRandomAnime() = animeRepository.getRandomAnime()

    override fun getAnimeSeasonalPagination(year: String, season: String) = animeRepository.getAnimeSeasonalPagination(year, season)

    override fun getFullDetailAnime(id: String) = animeRepository.getFullDetailAnime(id)

    override fun getRecommendationAnime(id: String) = animeRepository.getRecommendationAnime(id)

    override fun getStatisticAnime(id: String) = animeRepository.getStatisticAnime(id)

    override fun getCharacterAnime(id: String) = animeRepository.getCharacterAnime(id)

    override fun getEpisodesAnime(id: String) = animeRepository.getEpisodesAnime(id)

    override fun getNewsAnime(id: String) = animeRepository.getNewsAnime(id)

    override fun getReviewAnime(id: String) = animeRepository.getReviewAnime(id)

    override fun getPicturesAnime(id: String) = animeRepository.getPictureAnime(id)

}