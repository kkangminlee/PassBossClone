package org.passorder.boss.presentation.main.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.passorder.boss.R
import org.passorder.boss.databinding.FragmentHistoryBinding
import org.passorder.boss.presentation.main.history.dialog.DatePickerDialog
import org.passorder.data.model.request.RequestOrder
import org.passorder.domain.PassDataStore
import org.passorder.domain.entity.SetCount
import org.passorder.ui.base.BindingFragment
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class HistoryFragment: BindingFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    @Inject
    lateinit var dataStore: PassDataStore
    private var adapter: HistoryPagingAdapter? = null
    private val viewModel by viewModels<HistoryViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        adapter = HistoryPagingAdapter {
        }
        binding.rvLastOrder.adapter = adapter
        val decoration = DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        binding.rvLastOrder.addItemDecoration(decoration)


    }

    private fun initEvent() {
        // 날짜 선택 다이어로그 보여주는 리스너, 종료하면 기간 값 String 으로 받음
        binding.clDate.setOnClickListener {
            val dialog = DatePickerDialog()
            dialog.onDateListener { start, end ->
                setPeriod(start,end)
            }
            dialog.show(requireFragmentManager(), "dialog")
        }
    }

    private fun observe() {
        // 서버 통신 후 받은 총 판매량
        viewModel.orderCount.flowWithLifecycle(lifecycle)
            .onEach {
                binding.tvOrderCount.text = "${it}개"
            }.launchIn(lifecycleScope)

        // 서버 통신 후 받은 총 매출액
        viewModel.money.flowWithLifecycle(lifecycle)
            .onEach {
                binding.tvMoney.text = "${it}원"
            }.launchIn(lifecycleScope)
    }

    // 다이얼로그에서 월 선택 후 해당 값으로 판매량, 총 매출, 주문 내역(페이징) 서버 통신
    private fun setPeriod(start: String, end: String) {
        val includeDump = gsonToJson(listOf(dataStore.storeUUID))
        val orderCount = gsonToJson(listOf(false,false,false,false,false,false,false,false))
        val totalMoney = gsonToJson(listOf(false,false,false,false,false,false,false,true))
        binding.tvStartDetail.text = start
        binding.tvEndDetail.text = end
        viewModel.getOrderCount(SetCount(start,end,includeDump, orderCount))
        viewModel.getTotalMoney(SetCount(start,end,includeDump,totalMoney))

        // orderList 함수에서 받은 PagingData 값을 PagingAdapter 에 넣음
        lifecycleScope.launch {
            viewModel.orderList(RequestOrder(1,10,1,start,end)).flowWithLifecycle(lifecycle)
                .collectLatest {
                    adapter?.submitData(it)
                }
        }
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