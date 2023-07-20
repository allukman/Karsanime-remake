package com.karsatech.karsanime.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.karsatech.karsanime.core.data.source.remote.network.PeopleService
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleResponse

class PeoplePagingSource(private val peopleService: PeopleService): PagingSource<Int, DetailPeopleResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailPeopleResponse> =
        try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = peopleService.getTopPeople(position, 25).data

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, DetailPeopleResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}