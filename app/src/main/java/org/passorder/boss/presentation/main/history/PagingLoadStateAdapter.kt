package org.passorder.boss.presentation.main.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import org.passorder.boss.databinding.ItemLoadStateBinding

// 페이징 로딩 상태 관리 어뎁터
class PagingLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PagingLoadStateAdapter.PagingLoadStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingLoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    // 다음 페이지 로딩 실패시 버튼 클릭하면 재시도 로직
    class PagingLoadStateViewHolder(
        private val binding: ItemLoadStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state: LoadState) {
            with(binding) {
                btnRetry.setOnClickListener { retry() }
                isLoading = state is LoadState.Loading
                isError = state is LoadState.Error
                errorMessage = (state as? LoadState.Error)?.error?.message ?: ""
            }
        }
    }
}