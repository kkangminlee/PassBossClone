package org.passorder.boss.presentation.main.history

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.passorder.boss.databinding.ItemHistoryListBinding
import org.passorder.boss.databinding.ItemMenuListBinding
import org.passorder.boss.util.OrderStatus
import org.passorder.boss.util.TakeOut
import org.passorder.domain.entity.Order

// 지난 주문 내역 페이징 어뎁터
class HistoryPagingAdapter(private val itemClick: (Order) -> (Unit)) :
    PagingDataAdapter<Order, HistoryPagingAdapter.HistoryViewHolder>(DIFF_UTIL) {
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            ItemHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, itemClick)
    }

    class HistoryViewHolder(
        private val binding: ItemHistoryListBinding,
        private val itemClick: (Order) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Order) {
            val inflater = LayoutInflater.from(binding.root.context)
            with(binding) {
                binding.data = item

                TakeOut.values().forEach {
                    if (item.tableNumber == it.takeNo) {
                        tvId.text = "${item.nickName} / ${it.takeStatue}"
                    }
                }

                OrderStatus.values().forEach {
                    if (item.status == it.status) {
                        tvStatus.setBackgroundResource(it.background)
                        tvStatus.text = it.orderText
                    }
                }

                tvStatus.setOnClickListener {
                    itemClick(item)
                }

                // 주문 메뉴 LinearLayout 동적 추가
                menuContainer.run {
                    val createMenuBinding = { ItemMenuListBinding.inflate(inflater) }

                    fun addMenuItems() {
                        item.menus.map { menu ->
                            createMenuBinding().apply {
                                tvCoffee.text = menu.name
                                tvPrice.text = "${menu.price}원"
                                tvCount.text = "${menu.count}개"

                                menu.options.map { option ->
                                    TextView(binding.root.context).apply {
                                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                                        text = option.name
                                    }
                                }.forEach {
                                    optionContainer.addView(it)
                                }
                            }
                        }.forEach {
                            addView(it.root)
                        }
                    }

                    fun addUsedCouponItems() {
                        item.usedCoupons.map { coupon ->
                            createMenuBinding().apply {
                                tvCoffee.text = coupon.couponName
                                tvPrice.text = "-${coupon.benefit}원"
                                root.removeView(tvCount)
                                root.removeView(optionContainer)
                            }
                        }.forEach {
                            addView(it.root)
                        }
                    }
                    removeAllViews()
                    addMenuItems()
                    addUsedCouponItems()
                }
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(
                oldItem: Order,
                newItem: Order
            ): Boolean {
                return oldItem.identifier == newItem.identifier
            }

            override fun areContentsTheSame(
                oldItem: Order,
                newItem: Order
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}