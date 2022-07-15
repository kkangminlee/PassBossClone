package org.passorder.boss.presentation.main

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
import org.passorder.domain.PassDataStore
import org.passorder.ui.base.BindingActivity
import org.passorder.ui.context.stringListFrom
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    @Inject
    lateinit var dataStore: PassDataStore
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        binding.viewModel = viewModel
        binding.spinnerTime.apply {
            adapter = ArrayAdapter.createFromResource(this@MainActivity, R.array.spinner_List, android.R.layout.simple_spinner_dropdown_item)
            setSelection(dataStore.minTime)

            binding.vpMenu.adapter = MainViewPagerAdapter(supportFragmentManager.fragmentFactory, this@MainActivity)

            TabLayoutMediator(binding.tlMenu, binding.vpMenu) { tab, position ->
                tab.text = stringListFrom(R.array.tab_list)[position]
            }.attach()
        }
    }

    private fun initEvent() {
        val time = listOf(0,5,10,15,20,30,40,60)
        binding.ivCheckOpen.setOnClickListener {
            viewModel.gateStore(!binding.ivCheckOpen.isSelected)
        }

        binding.spinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                dataStore.minTime = position
                viewModel.setMinTime(time[dataStore.minTime])
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
    }
}