package com.karsatech.karsanime.core.utils

import com.karsatech.karsanime.core.data.source.local.entity.AnimeEntity
import com.karsatech.karsanime.core.data.source.local.entity.TourismEntity
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Tourism

object DataMapper {
//    fun mapResponsesToEntities(input: List<TourismResponse>): List<TourismEntity> {
//        val tourismList = ArrayList<TourismEntity>()
//        input.map {
//            val tourism = TourismEntity(
//                tourismId = it.id,
//                description = it.description,
//                name = it.name,
//                address = it.address,
//                latitude = it.latitude,
//                longitude = it.longitude,
//                like = it.like,
//                image = it.image,
//                isFavorite = false
//            )
//            tourismList.add(tourism)
//        }
//        return tourismList
//    }

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

    fun mapEntitiesToDomain(input: List<AnimeEntity>): List<Anime> =
        input.map {
            Anime(
                animeId = it.animeId,
                title = it.title,
                image = it.image,
                synopsis = it.synopsis
            )
        }

    fun mapDomainToEntity(input: Anime) = AnimeEntity(
        animeId = input.animeId,
        title = input.title,
        image = input.image,
        synopsis = input.synopsis
    )

//    fun mapEntitiesToDomain(input: List<TourismEntity>): List<Tourism> =
//        input.map {
//            Tourism(
//                tourismId = it.tourismId,
//                description = it.description,
//                name = it.name,
//                address = it.address,
//                latitude = it.latitude,
//                longitude = it.longitude,
//                like = it.like,
//                image = it.image,
//                isFavorite = it.isFavorite
//            )
//        }
//
//    fun mapDomainToEntity(input: Tourism) = TourismEntity(
//        tourismId = input.tourismId,
//        description = input.description,
//        name = input.name,
//        address = input.address,
//        latitude = input.latitude,
//        longitude = input.longitude,
//        like = input.like,
//        image = input.image,
//        isFavorite = input.isFavorite
//    )
}