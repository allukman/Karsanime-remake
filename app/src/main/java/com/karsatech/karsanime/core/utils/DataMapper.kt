package com.karsatech.karsanime.core.utils

import android.provider.Contacts
import com.karsatech.karsanime.core.data.source.local.entity.AnimeEntity
import com.karsatech.karsanime.core.data.source.local.entity.MangaEntity
import com.karsatech.karsanime.core.data.source.local.entity.PeopleEntity
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleResponse
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.domain.model.People

object DataMapper {
    fun mapAnimeEntitiesToDomain(input: List<AnimeEntity>): List<Anime> =
        input.map {
            Anime(
                animeId = it.animeId,
                title = it.title,
                image = it.image,
                synopsis = it.synopsis,
                rating = it.rating,
                score = it.score,
                rank = it.rank,
                members = it.members,
                popularity = it.popularity,
                favorites = it.favorites,
                status = it.status,
                episodes = it.episodes
            )
        }

    fun mapDomainToEntityAnime(input: Anime) = AnimeEntity(
        animeId = input.animeId,
        title = input.title,
        image = input.image,
        synopsis = input.synopsis,
        rating = input.rating,
        score = input.score,
        rank = input.rank,
        members = input.members,
        popularity = input.popularity,
        favorites = input.favorites,
        status = input.status,
        episodes = input.episodes
    )

    fun mapMangaEntitiesToDomain(input: List<MangaEntity>): List<Manga> =
        input.map {
            Manga(
                mangaId = it.mangaId,
                title = it.title,
                image = it.image,
                chapters = it.chapters,
                volumes = it.volumes,
                status = it.status,
                score = it.score,
                rank = it.ranking,
                members = it.member,
                popularity = it.popularity,
                favorites = it.favorites,
                synopsis = it.synopsis
            )
        }

    fun mapDomainToEntityManga(input: Manga) = MangaEntity(
        mangaId = input.mangaId,
        title = input.title,
        image = input.image,
        chapters = input.chapters,
        volumes = input.volumes,
        status = input.status,
        score = input.score,
        ranking = input.rank,
        member = input.members,
        popularity = input.popularity,
        favorites = input.favorites,
        synopsis = input.synopsis
    )

    fun mapPeopleEntitiesToDomain(input: List<PeopleEntity>): List<People> =
        input.map {
            People(
                peopleId = it.peopleId,
                name = it.name,
                image = it.image,
                favorites = it.favorites,
                birthday = it.birthday,
                about = it.about
            )
        }

    fun mapDomainToEntityPeople(input: People) = PeopleEntity(
        peopleId = input.peopleId,
        name = input.name,
        image = input.image,
        favorites = input.favorites,
        birthday = input.birthday,
        about = input.about
    )

    fun mapApiResponseToAnimeModel(input: DetailGeneralResponse): Anime =
        Anime(
            animeId = input.malId.toString(),
            title = input.title.toString(),
            image = input.images?.jpg?.largeImageUrl.toString(),
            synopsis = input.synopsis.toString(),
            rating = input.rating.toString(),
            score = input.score.toString(),
            rank = input.rank.toString(),
            members = input.members.toString(),
            popularity = input.popularity.toString(),
            favorites = input.favorite.toString(),
            status = input.status.toString(),
            episodes = input.episodes.toString()
        )

    fun mapApiResponseToMangaModel(input: DetailGeneralResponse): Manga =
        Manga(
            mangaId = input.malId.toString(),
            title = input.title.toString(),
            image = input.images?.jpg?.largeImageUrl.toString(),
            synopsis = input.synopsis.toString(),
            score = input.score.toString(),
            rank = input.rank.toString(),
            members = input.members.toString(),
            popularity = input.popularity.toString(),
            favorites = input.favorite.toString(),
            status = input.status.toString(),
            chapters = input.chapters.toString(),
            volumes = input.volumes.toString()
        )

    fun apiResponseToPeopleModel(input: DetailPeopleResponse): People =
        People(
            peopleId = input.malId.toString(),
            name = input.name.toString(),
            image = input.images?.jpg?.imageUrl.toString(),
            birthday = input.birthday.toString(),
            favorites = input.favorites.toString(),
            about = input.about.toString()
        )
}