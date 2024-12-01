package com.ead.app.monoschinos.presentation.app.util

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<Key : Any, Value : Any> : PagingSource<Key, Value>() {

    /**
     * Abstract method for specific data fetching logic.
     */
    protected abstract suspend fun fetchData(params: LoadParams<Key>): List<Value>

    /**
     * Implementation of `load`, including retries and error handling.
     */
    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value> {
        return try {
            // Fetch data using the abstract method
            val data = fetchData(params)
            LoadResult.Page(
                data = data,
                prevKey = getPreviousKey(params.key),
                nextKey = getNextKey(params.key, data)
            )
        } catch (e: Exception) {
            // Handle exceptions and convert them to LoadResult.Error
            LoadResult.Error(e)
        }
    }

    /**
     * Override to define previous key logic for pagination.
     */
    protected open fun getPreviousKey(currentKey: Key?): Key? = null

    /**
     * Override to define next key logic for pagination.
     */
    protected open fun getNextKey(currentKey: Key?, data: List<Value>): Key? = null

    /**
     * Refresh key logic for paging state.
     */
    override fun getRefreshKey(state: PagingState<Key, Value>): Key? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}
