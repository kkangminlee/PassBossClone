package org.passorder.boss.presentation.main.history

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.passorder.boss.R
import org.passorder.boss.databinding.FragmentHistoryBinding
import org.passorder.boss.presentation.main.history.dialog.DatePickerDialog
import org.passorder.boss.presentation.main.order.OrderListAdapter
import org.passorder.domain.PassDataStore
import org.passorder.domain.entity.SetCount
import org.passorder.domain.entity.SetOrder
import org.passorder.ui.base.BindingFragment
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class HistoryFragment: BindingFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    @Inject
    lateinit var dataStore: PassDataStore
    private var adapter: OrderListAdapter? = null
    private val viewModel by viewModels<HistoryViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        adapter = OrderListAdapter{

        }
        binding.rvLastOrder.adapter = adapter
        val decoration = DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        binding.rvLastOrder.addItemDecoration(decoration)


    }

    private fun initEvent() {
        binding.clDate.setOnClickListener {
            val dialog = DatePickerDialog()
            dialog.onDateListener { start, end ->
                setPeriod(start,end)
            }
            dialog.show(requireFragmentManager(), "dialog")
        }
    }

    private fun observe() {
        viewModel.currentOrder.flowWithLifecycle(lifecycle)
            .onEach {
                binding.clEmpty.isVisible = it.isEmpty()
                binding.rvLastOrder.isVisible = it.isNotEmpty()
                adapter?.submitList(it)
            }.launchIn(lifecycleScope)

        viewModel.orderCount.flowWithLifecycle(lifecycle)
            .onEach {
                binding.tvOrderCount.text = "${it}개"
            }.launchIn(lifecycleScope)

        viewModel.money.flowWithLifecycle(lifecycle)
            .onEach {
                binding.tvMoney.text = "${it}원"
            }.launchIn(lifecycleScope)
    }

    private fun setPeriod(start: String, end: String) {
        val includeDump = gsonToJson(listOf(dataStore.storeUUID))
        val orderCount = gsonToJson(listOf(false,false,false,false,false,false,false,false))
        val totalMoney = gsonToJson(listOf(false,false,false,false,false,false,false,true))
        binding.tvStartDetail.text = start
        binding.tvEndDetail.text = end
        viewModel.orderList(SetOrder(1,10,1,start,end))
        viewModel.getOrderCount(SetCount(start,end,includeDump, orderCount))
        viewModel.getTotalMoney(SetCount(start,end,includeDump,totalMoney))
    }

    override fun onResume() {
        super.onResume()
        val cal = Calendar.getInstance()
        val yearMonth = "${cal[Calendar.YEAR]}-${String.format("%02d",cal[Calendar.MONTH]+1)}"
        val startDate = "$yearMonth-${String.format("%02d",cal.getActualMinimum(Calendar.DAY_OF_MONTH))}"
        val endDate = "$yearMonth-${String.format("%02d",cal.getActualMaximum(Calendar.DAY_OF_MONTH))}"
        setPeriod(startDate, endDate)
    }

    private fun <T> gsonToJson(list: List<T>): String? {
        return Gson().toJson(list)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}