package com.karsatech.karsanime.core.domain.usecase.favorite

import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.domain.model.Character
import com.karsatech.karsanime.core.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(private val favoriteRepository: FavoriteRepository): FavoriteUseCase {

    override fun getAllFavoriteAnime() = favoriteRepository.getAllFavoriteAnime()
    override fun getFavoriteAnimeById(favAnimeId: String) = favoriteRepository.getFavoriteAnimeById(favAnimeId)
    override fun setFavoriteAnime(favAnime: Anime) = favoriteRepository.setFavoriteAnime(favAnime)
    override fun removeFavoriteAnime(favAnimeId: String) = favoriteRepository.removeFavoriteAnime(favAnimeId)

    override fun getAllFavoriteManga() = favoriteRepository.getAllFavoriteManga()
    override fun getFavoriteMangaById(favMangaId: String) = favoriteRepository.getFavoriteMangaById(favMangaId)
    override fun setFavoriteManga(favManga: Manga) = favoriteRepository.setFavoriteManga(favManga)
    override fun removeFavoriteManga(favMangaId: String) = favoriteRepository.removeFavoriteManga(favMangaId)

    override fun getAllFavoritePeople() = favoriteRepository.getAllFavoritePeople()
    override fun getFavoritePeopleById(favPeopleId: String) = favoriteRepository.getFavoritePeopleById(favPeopleId)
    override fun setFavoritePeople(favPeople: People) = favoriteRepository.setFavoritePeople(favPeople)
    override fun removeFavoritePeople(favPeopleId: String) = favoriteRepository.removeFavoritePeople(favPeopleId)

    override fun getAllFavoriteCharacter() = favoriteRepository.getAllFavoriteCharacter()
    override fun getFavoriteCharacterById(favCharacterId: String) = favoriteRepository.getFavoriteCharacterById(favCharacterId)
    override fun setFavoriteCharacter(favCharacter: Character) = favoriteRepository.setFavoriteCharacter(favCharacter)
    override fun removeFavoriteCharacter(favCharacterId: String) = favoriteRepository.removeFavoriteCharacter(favCharacterId)

}