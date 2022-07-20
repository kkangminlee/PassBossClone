package org.passorder.boss.presentation.notice

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import org.passorder.boss.R
import org.passorder.boss.databinding.ActivityNoticeBinding
import org.passorder.boss.presentation.notice.menu.NoticeMenuFragment
import org.passorder.boss.presentation.notice.store.NoticeStoreFragment
import org.passorder.ui.base.BindingActivity

@AndroidEntryPoint
class NoticeActivity : BindingActivity<ActivityNoticeBinding>(R.layout.activity_notice) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()
    }

    private fun initView() {
        binding.tlNotice.apply {
            addTab(newTab().setText("메뉴 품절"))
            addTab(newTab().setText("매장 미오픈"))
        }

        binding.tbNotice.apply {
            title = "소식"
            setSupportActionBar(this)
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }


        supportFragmentManager.commit {
            add(R.id.notice_container, NoticeMenuFragment())
        }
    }

    private fun initEvent() {
        binding.tlNotice.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    SOLD_OUT -> replaceFragment(NoticeMenuFragment())
                    NOT_OPEN -> replaceFragment(NoticeStoreFragment())
                    else -> throw IllegalStateException("해당 Fragment는 존재하지 않습니다.")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.notice_container, fragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val SOLD_OUT = 0
        private const val NOT_OPEN = 1
    }
}