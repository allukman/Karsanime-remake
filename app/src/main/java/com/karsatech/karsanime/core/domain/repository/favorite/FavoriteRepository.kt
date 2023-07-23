package com.karsatech.karsanime.core.domain.repository.favorite

import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.local.LocalDataSource
import com.karsatech.karsanime.core.data.source.local.entity.AnimeEntity
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.utils.AppExecutors
import com.karsatech.karsanime.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val localDataSource: LocalDataSource
) : IFavoriteRepository {

    override fun getAllFavorite(): Flow<List<Anime>> {
        return localDataSource.getAllFavoriteAnime().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteAnimeById(favAnimeId: String): Flow<List<Anime>> {
        return localDataSource.getFavoriteAnimeById(favAnimeId).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteAnime(favAnime: Anime) {
        val animeEntity = DataMapper.mapDomainToEntity(favAnime)
        appExecutors.diskIO().execute { localDataSource.setFavoriteAnime(animeEntity) }
    }

    override fun removeFavoriteAnime(favAnimeId: String) {
        appExecutors.diskIO().execute { localDataSource.removeFavoriteAnime(favAnimeId) }
    }
}