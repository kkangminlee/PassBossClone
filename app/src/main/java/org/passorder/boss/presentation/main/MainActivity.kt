package org.passorder.boss.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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
import org.passorder.boss.presentation.notice.NoticeActivity
import org.passorder.domain.PassDataStore
import org.passorder.ui.base.BindingActivity
import org.passorder.ui.context.stringListFrom
import org.passorder.ui.context.toast
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    @Inject
    lateinit var dataStore: PassDataStore
    private val viewModel by viewModels<MainViewModel>()
    val time = listOf(0, 5, 10, 15, 20, 30, 40, 60)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        binding.viewModel = viewModel
        binding.spinnerTime.apply {
            adapter = ArrayAdapter.createFromResource(
                this@MainActivity,
                R.array.spinner_List,
                android.R.layout.simple_spinner_dropdown_item
            )

            binding.vpMenu.adapter =
                MainViewPagerAdapter(supportFragmentManager.fragmentFactory, this@MainActivity)

            TabLayoutMediator(binding.tlMenu, binding.vpMenu) { tab, position ->
                tab.text = stringListFrom(R.array.tab_list)[position]
            }.attach()
        }
    }

    private fun initEvent() {
        var isFirstSelected = true

        // 매장 열고 닫기 서버 통신 이벤트
        binding.ivCheckOpen.setOnClickListener {
            viewModel.gateStore(!binding.ivCheckOpen.isSelected)
        }

        // 공지사항 뷰로 이동하는 이벤트
        binding.ivAlarm.setOnClickListener {
            Intent(this, NoticeActivity::class.java).apply {
                startActivity(this)
            }
        }

        // 메뉴 최소 수령시간 설정 이벤트
        binding.spinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if (isFirstSelected) {
                    isFirstSelected = false
                } else {
                    viewModel.setMinTime(time[position])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun observe() {
        viewModel.isOpenState.flowWithLifecycle(lifecycle)
            .onEach {
                binding.ivCheckOpen.isSelected = it
            }.launchIn(lifecycleScope)

        viewModel.storeInfo.flowWithLifecycle(lifecycle)
            .onEach {
                binding.apply {
                    tvName.text = it.name
                    spinnerTime.setSelection(time.indexOf(it.minimumPickupTime))
                    ivCheckOpen.isSelected = it.isOpen
                }
            }.launchIn(lifecycleScope)

        viewModel.errorMsg.flowWithLifecycle(lifecycle)
            .onEach {
                toast(it)
            }.launchIn(lifecycleScope)
    }
}