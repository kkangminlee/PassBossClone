package org.passorder.boss.presentation.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.passorder.boss.databinding.ItemNoticeCommonBinding
import org.passorder.domain.entity.Notice

// 공지사항(매장 미오픈, 메뉴 품절)을 위한 리사이클러뷰 어뎁터
class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.NoticeStoreViewHolder>() {
    private val data = mutableListOf<Notice>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeStoreViewHolder {
        val binding =
            ItemNoticeCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeStoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeStoreViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    // 아이템 추가 함수
    fun setItems(newItems: List<Notice>) {
        data.clear()
        data.addAll(newItems)
        notifyDataSetChanged()
    }

    class NoticeStoreViewHolder(
        private val binding: ItemNoticeCommonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(notice: Notice) {
            binding.data = notice
        }

    }
}