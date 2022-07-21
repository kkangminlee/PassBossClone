package org.passorder.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.passorder.data.model.request.RequestOrder
import org.passorder.data.model.response.ResponseOrder
import org.passorder.data.service.OrderService

class OrderPagingSource(
    private val service: OrderService,
    private val request: RequestOrder
) : PagingSource<Int, ResponseOrder>() {
    // 현재 페이지 포지션을 가지고 있는 로직
    override fun getRefreshKey(state: PagingState<Int, ResponseOrder>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // 스크롤을 할 시 현재 페이지를 계속해서 증가하며 서버통신을 할 수 있는 로직, 위로 스크롤 할 시 현재페이즈를 감소시키며 서버통신
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

        val nextPosition = if(response.isEmpty()) null else currentPosition + 1
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