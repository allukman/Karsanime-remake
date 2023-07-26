package com.karsatech.karsanime.core.data.source.local

import com.karsatech.karsanime.core.data.source.local.entity.AnimeEntity
import com.karsatech.karsanime.core.data.source.local.entity.CharacterEntity
import com.karsatech.karsanime.core.data.source.local.entity.MangaEntity
import com.karsatech.karsanime.core.data.source.local.entity.PeopleEntity
import com.karsatech.karsanime.core.data.source.local.room.AnimeDao
import com.karsatech.karsanime.core.data.source.local.room.CharacterDao
import com.karsatech.karsanime.core.data.source.local.room.MangaDao
import com.karsatech.karsanime.core.data.source.local.room.PeopleDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val animeDao: AnimeDao,
    private val mangaDao: MangaDao,
    private val peopleDao: PeopleDao,
    private val characterDao: CharacterDao
) {

    fun getAllFavoriteAnime(): Flow<List<AnimeEntity>> = animeDao.getAllFavoriteAnime()

    fun getFavoriteAnimeById(animeId: String): Flow<List<AnimeEntity>> =
        animeDao.getFavoriteById(animeId)

    fun setFavoriteAnime(anime: AnimeEntity) {
        animeDao.insertFavoriteAnime(anime)
    }

    fun removeFavoriteAnime(animeId: String) {
        animeDao.removeFavoriteAnime(animeId)
    }

    fun getAllFavoriteManga(): Flow<List<MangaEntity>> = mangaDao.getAllFavoriteManga()

    fun getFavoriteMangaById(mangaId: String): Flow<List<MangaEntity>> =
        mangaDao.getFavoriteById(mangaId)

    fun setFavoriteManga(manga: MangaEntity) {
        mangaDao.insertFavoriteManga(manga)
    }

    fun removeFavoriteManga(mangaId: String) {
        mangaDao.removeFavoriteManga(mangaId)
    }

    fun getAllFavoritePeople(): Flow<List<PeopleEntity>> = peopleDao.getAllFavoritePeople()

    fun getFavoritePeopleById(peopleId: String): Flow<List<PeopleEntity>> =
        peopleDao.getFavoritePeopleById(peopleId)

    fun setFavoritePeople(people: PeopleEntity) {
        peopleDao.insertFavoritePeople(people)
    }

    fun removeFavoritePeople(peopleId: String) {
        peopleDao.removeFavoritePeople(peopleId)
    }

    fun getAllFavoriteCharacter(): Flow<List<CharacterEntity>> = characterDao.getAllFavoriteCharacter()

    fun getFavoriteCharacterById(characterId: String): Flow<List<CharacterEntity>> =
        characterDao.getFavoriteCharacterById(characterId)

    fun setFavoriteCharacter(character: CharacterEntity) {
        characterDao.insertFavoriteCharacter(character)
    }

    fun removeFavoriteCharacter(characterId: String) {
        characterDao.removeFavoriteCharacter(characterId)
    }

}

