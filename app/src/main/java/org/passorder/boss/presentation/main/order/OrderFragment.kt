package org.passorder.boss.presentation.main.order

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.passorder.boss.R
import org.passorder.boss.databinding.FragmentOrderBinding
import org.passorder.domain.entity.SetOrder
import org.passorder.ui.base.BindingFragment

@AndroidEntryPoint
class OrderFragment : BindingFragment<FragmentOrderBinding>(R.layout.fragment_order) {
    private var adapter: OrderListAdapter? = null
    private val viewModel by viewModels<OrderViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observe()
    }

    private fun initView() {
        adapter = OrderListAdapter{
            viewModel.putOrderStatus(it.identifier)
        }
        binding.rvOrderList.adapter = adapter
    }

    private fun observe() {
        viewModel.currentOrder.flowWithLifecycle(lifecycle)
            .onEach {
                binding.clEmpty.isVisible = it.isEmpty()
                binding.rvOrderList.isVisible = it.isNotEmpty()
                adapter?.submitList(it)
            }.launchIn(lifecycleScope)

        viewModel.orderStatus.flowWithLifecycle(lifecycle)
            .onEach {
                //Todo Status 응답값 처리
            }.launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        viewModel.orderList(SetOrder(1, 9999, 0, null, null))
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}