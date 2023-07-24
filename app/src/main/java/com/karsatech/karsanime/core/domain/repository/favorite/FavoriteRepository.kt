package com.karsatech.karsanime.core.domain.repository.favorite

import com.karsatech.karsanime.core.data.source.local.LocalDataSource
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Manga
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

    override fun getAllFavoriteAnime(): Flow<List<Anime>> {
        return localDataSource.getAllFavoriteAnime().map {
            DataMapper.mapAnimeEntitiesToDomain(it)
        }
    }

    override fun getFavoriteAnimeById(favAnimeId: String): Flow<List<Anime>> {
        return localDataSource.getFavoriteAnimeById(favAnimeId).map {
            DataMapper.mapAnimeEntitiesToDomain(it)
        }
    }

    override fun setFavoriteAnime(favAnime: Anime) {
        val animeEntity = DataMapper.mapDomainToEntityAnime(favAnime)
        appExecutors.diskIO().execute { localDataSource.setFavoriteAnime(animeEntity) }
    }

    override fun removeFavoriteAnime(favAnimeId: String) {
        appExecutors.diskIO().execute { localDataSource.removeFavoriteAnime(favAnimeId) }
    }

    override fun getAllFavoriteManga(): Flow<List<Manga>> {
        return localDataSource.getAllFavoriteManga().map {
            DataMapper.mapMangaEntitiesToDomain(it)
        }
    }

    override fun getFavoriteMangaById(favMangaId: String): Flow<List<Manga>> {
        return localDataSource.getFavoriteMangaById(favMangaId).map {
            DataMapper.mapMangaEntitiesToDomain(it)
        }
    }

    override fun setFavoriteManga(favManga: Manga) {
        val mangaEntity = DataMapper.mapDomainToEntityManga(favManga)
        appExecutors.diskIO().execute { localDataSource.setFavoriteManga(mangaEntity) }
    }

    override fun removeFavoriteManga(favMangaId: String) {
        appExecutors.diskIO().execute { localDataSource.removeFavoriteManga(favMangaId) }
    }
}