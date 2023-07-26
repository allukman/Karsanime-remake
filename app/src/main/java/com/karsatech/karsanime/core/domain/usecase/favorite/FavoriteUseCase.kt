package com.karsatech.karsanime.core.domain.usecase.favorite

import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {

    fun getAllFavoriteAnime(): Flow<List<Anime>>
    fun getFavoriteAnimeById(favAnimeId: String): Flow<List<Anime>>
    fun setFavoriteAnime(favAnime: Anime)
    fun removeFavoriteAnime(favAnimeId: String)

    fun getAllFavoriteManga(): Flow<List<Manga>>
    fun getFavoriteMangaById(favMangaId: String): Flow<List<Manga>>
    fun setFavoriteManga(favManga: Manga)
    fun removeFavoriteManga(favMangaId: String)

    fun getAllFavoritePeople(): Flow<List<People>>
    fun getFavoritePeopleById(favPeopleId: String): Flow<List<People>>
    fun setFavoritePeople(favPeople: People)
    fun removeFavoritePeople(favPeopleId: String)

    fun getAllFavoriteCharacter(): Flow<List<Character>>
    fun getFavoriteCharacterById(favCharacterId: String): Flow<List<Character>>
    fun setFavoriteCharacter(favCharacter: Character)
    fun removeFavoriteCharacter(favCharacterId: String)

}