package org.passorder.boss.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.passorder.boss.presentation.main.history.HistoryFragment
import org.passorder.boss.presentation.main.order.OrderFragment
import org.passorder.boss.presentation.main.other.MenuFragment

class MainViewPagerAdapter(private val factory: FragmentFactory, private val fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        val classLoader = fragment.classLoader
        return when (position) {
            0 -> factory.instantiate(classLoader, OrderFragment::class.java.name)
            1 -> factory.instantiate(classLoader, HistoryFragment::class.java.name)
            else -> factory.instantiate(classLoader, MenuFragment::class.java.name)
        }
    }
}