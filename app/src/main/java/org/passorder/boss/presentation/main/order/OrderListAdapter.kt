package org.passorder.boss.presentation.main.order

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.passorder.boss.databinding.ItemHistoryListBinding
import org.passorder.boss.databinding.ItemMenuListBinding
import org.passorder.boss.util.OrderStatus
import org.passorder.boss.util.TakeOut
import org.passorder.domain.entity.Order

class OrderListAdapter(private val itemClick: (Order, Int) -> (Unit)) :
    ListAdapter<Order, OrderListAdapter.OrderListViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val binding =
            ItemHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    class OrderListViewHolder(
        private val binding: ItemHistoryListBinding,
        private val itemClick: (Order, Int) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: Order, position: Int) {
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


                // 버튼 클릭시 데이터 클래스와, 해당하는 포지션을 같이 전달
                tvStatus.setOnClickListener {
                    itemClick(item, position)
                }

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