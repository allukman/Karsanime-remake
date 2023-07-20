package com.karsatech.karsanime.features.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.karsatech.karsanime.core.domain.usecase.people.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(peopleUseCase: PeopleUseCase): ViewModel() {

    val topPeoplePagination = peopleUseCase.getTopPeoplePagination().cachedIn(viewModelScope)

}