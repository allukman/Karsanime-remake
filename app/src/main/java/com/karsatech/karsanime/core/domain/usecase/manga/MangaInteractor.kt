package com.karsatech.karsanime.core.domain.usecase.manga

import com.karsatech.karsanime.core.domain.repository.manga.MangaRepository
import javax.inject.Inject

class MangaInteractor @Inject constructor(private val mangaRepository: MangaRepository): MangaUseCase {

    override fun getTopManga() = mangaRepository.getTopManga()

}