package org.passorder.boss.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import org.passorder.boss.databinding.ActivityMainBinding
import org.passorder.boss.presentation.main.history.HistoryFragment
import org.passorder.boss.presentation.main.order.OrderFragment
import org.passorder.boss.presentation.main.other.MenuFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
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

}