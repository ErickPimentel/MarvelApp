package com.erickpimentel.marvelapp.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.erickpimentel.marvelapp.domain.usecases.GetCharactersUseCase
import com.erickpimentel.marvelapp.domain.model.Character

class CharacterPagingSource(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val nameStartsWith: String?,
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val pageNumber = params.key ?: 0

            val offset = (pageNumber + 1) * PAGE_SIZE

            val response = getCharactersUseCase(
                nameStartsWith = if (nameStartsWith.isNullOrEmpty()) null else nameStartsWith,
                offset = offset,
                limit = PAGE_SIZE
            )

            val data = response.body()?.data

            val characters = data?.results?.map { it.toCharacter() } ?: emptyList()

            val prevPage = if (pageNumber > 0) pageNumber - 1 else null

            val nextPage = if (data?.count!! < PAGE_SIZE) null else pageNumber + 1

            LoadResult.Page(
                data = characters,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}