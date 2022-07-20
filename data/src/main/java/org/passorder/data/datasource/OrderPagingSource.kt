package org.passorder.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.passorder.data.model.request.RequestOrder
import org.passorder.data.model.response.ResponseOrder
import org.passorder.data.service.OrderService

class OrderPagingSource(
    private val service: OrderService,
    private val request: RequestOrder
) : PagingSource<Int, ResponseOrder>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseOrder>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseOrder> {
        val currentPosition = params.key ?: 1
        val response = runCatching {
            service.getOrderList(
                page = currentPosition,
                filter = 1,
                start = request.start,
                end = request.end
            )
        }.getOrElse { return LoadResult.Error(it) }

        val nextPosition = if (response.isEmpty()) null else currentPosition + 1
        val previousPosition =
            if (currentPosition != 0) null else currentPosition - 1
        return runCatching {
            LoadResult.Page(
                data = response,
                prevKey = previousPosition,
                nextKey = nextPosition
            )
        }.getOrElse { LoadResult.Error(it) }
    }
}