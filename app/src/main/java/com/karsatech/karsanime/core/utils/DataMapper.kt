package com.karsatech.karsanime.core.utils

import com.karsatech.karsanime.core.data.source.local.entity.AnimeEntity
import com.karsatech.karsanime.core.data.source.local.entity.MangaEntity
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.domain.model.Tourism

object DataMapper {
    fun mapResponsesToEntities(input: List<DetailGeneralResponse>): List<AnimeEntity> {
        val animeList = ArrayList<AnimeEntity>()
        input.map {
            val anime = AnimeEntity(
                animeId = it.malId.toString(),
                title = it.title ?: "",
                image = it.images?.jpg?.largeImageUrl ?: "",
                synopsis = it.synopsis ?: ""
            )
            animeList.add(anime)
        }
        return animeList
    }

    fun entitiesToDomain(input: AnimeEntity): Anime =
        Anime(
            animeId = input.animeId,
            title = input.title,
            image = input.image,
            synopsis = input.synopsis
        )

    fun mapAnimeEntitiesToDomain(input: List<AnimeEntity>): List<Anime> =
        input.map {
            Anime(
                animeId = it.animeId,
                title = it.title,
                image = it.image,
                synopsis = it.synopsis
            )
        }

    fun mapDomainToEntityAnime(input: Anime) = AnimeEntity(
        animeId = input.animeId,
        title = input.title,
        image = input.image,
        synopsis = input.synopsis
    )

    fun mapMangaEntitiesToDomain(input: List<MangaEntity>): List<Manga> =
        input.map {
            Manga(
                mangaId = it.mangaId,
                title = it.title,
                image = it.image,
                chapters = it.chapters,
                volumes = it.volumes,
                status = it.status
            )
        }

    fun mapDomainToEntityManga(input: Manga) = MangaEntity(
        mangaId = input.mangaId,
        title = input.title,
        image = input.image,
        chapters = input.chapters,
        volumes = input.volumes,
        status = input.status
    )


}