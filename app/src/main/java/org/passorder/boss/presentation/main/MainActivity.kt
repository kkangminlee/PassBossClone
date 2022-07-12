package org.passorder.boss.presentation.main

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.passorder.boss.R
import org.passorder.boss.databinding.ActivityMainBinding
import org.passorder.boss.presentation.main.history.HistoryFragment
import org.passorder.boss.presentation.main.order.OrderFragment
import org.passorder.boss.presentation.main.other.MenuFragment
import org.passorder.ui.base.BindingActivity

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        binding.viewModel = viewModel
        binding.spinnerTime.adapter = ArrayAdapter.createFromResource(this, R.array.itemList, android.R.layout.simple_spinner_item)
        val tabText = listOf("주문", "지난주문", "메뉴", "분석", "스토리", "적립내역")

        binding.vpMenu.adapter = MainViewPagerAdapter(this).apply {
            fragmentList = listOf(
                OrderFragment(),
                HistoryFragment(),
                MenuFragment(),
                MenuFragment(),
                MenuFragment(),
                MenuFragment()
            )
        }

        TabLayoutMediator(binding.tlMenu, binding.vpMenu) { tab, position ->
            tab.text = tabText[position]
        }.attach()
    }

    private fun initEvent() {
        binding.ivCheckOpen.setOnClickListener {
            viewModel.gateStore(!binding.ivCheckOpen.isSelected)
            viewModel.setMinTime(5)
        }

    }

    private fun observe() {
        viewModel.isOpenState.flowWithLifecycle(lifecycle)
            .onEach {
                binding.ivCheckOpen.isSelected = it
            }.launchIn(lifecycleScope)
    }
}