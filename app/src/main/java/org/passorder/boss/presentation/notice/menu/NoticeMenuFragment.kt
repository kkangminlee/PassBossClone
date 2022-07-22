package org.passorder.boss.presentation.notice.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.passorder.boss.R
import org.passorder.boss.databinding.FragmentNoticeMenuBinding
import org.passorder.boss.presentation.notice.NoticeAdapter
import org.passorder.boss.presentation.notice.NoticeViewModel
import org.passorder.ui.base.BindingFragment
import org.passorder.ui.fragment.toast

@AndroidEntryPoint
class NoticeMenuFragment: BindingFragment<FragmentNoticeMenuBinding>(R.layout.fragment_notice_menu) {
    private val viewModel by viewModels<NoticeViewModel>()
    private var adapter : NoticeAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observe()
    }

    private fun initView() {
        adapter = NoticeAdapter()
        binding.rvMenu.adapter = adapter

        // 서버통신을 위한 Date 설정 후 뷰모델에서 서버통신하는 로직
        viewModel.setData(1)
    }

    private fun observe() {
        // 매장 미오픈 알람 값 리사이클러뷰 어뎁터에 적용
        viewModel.noticeValue.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                adapter?.setItems(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorMsg.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                toast(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}