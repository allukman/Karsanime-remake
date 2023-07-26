package com.karsatech.karsanime.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.karsatech.karsanime.core.data.source.remote.network.AnimeService
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem

class UpcomingAnimePagingSource (private val animeService: AnimeService): PagingSource<Int, DetailAnimeItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailAnimeItem> =
        try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = animeService.getUpcomingAnime(position, 25).data

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, DetailAnimeItem>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}