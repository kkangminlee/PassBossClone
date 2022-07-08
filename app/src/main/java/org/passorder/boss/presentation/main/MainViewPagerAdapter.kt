package org.passorder.boss.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val _fragmentList = mutableListOf<Fragment>()
    var fragmentList: List<Fragment> = _fragmentList
        set(value) {
            _fragmentList.addAll(value)
        }

    override fun getItemCount(): Int = _fragmentList.size

    override fun createFragment(position: Int): Fragment = _fragmentList[position]
}