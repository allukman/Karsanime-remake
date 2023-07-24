package com.karsatech.karsanime.features.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.domain.usecase.favorite.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailPeopleViewModel @Inject constructor(private val favoriteUseCase: FavoriteUseCase): ViewModel() {

    fun getFavoritePeopleByMalId(favId: String) = favoriteUseCase.getFavoritePeopleById(favId).asLiveData()

    fun setFavoritePeople(favPeople: People) = favoriteUseCase.setFavoritePeople(favPeople)

    fun setUnFavoritePeople(favId: String) = favoriteUseCase.removeFavoritePeople(favId)

}