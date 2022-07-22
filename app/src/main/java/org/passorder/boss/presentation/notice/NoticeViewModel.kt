package org.passorder.boss.presentation.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.passorder.domain.entity.Notice
import org.passorder.domain.repository.NoticeRepository
import org.passorder.ui.base.BaseViewModel
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val repository: NoticeRepository
): BaseViewModel() {
    private val _noticeValue = MutableStateFlow<List<Notice>>(listOf())
    val noticeValue = _noticeValue.asStateFlow()

    // 매장 미오픈 or 메뉴 품절 리스트 불러오는 서버통신 로직
    private fun getNotice(kind: Int, start:String, end: String) {
        viewModelScope.launch {
            runCatching {
                repository.getNotice(kind, start, end)
            }.onSuccess {
                _noticeValue.value = it
            }.onFailure {
                if (it is HttpException) {
                    _errorMsg.emit("서버 통신 에러 error code: ${it.code()}")
                }
            }
        }
    }

    // 오늘, 한달전 날짜 기간 선택 후 공지사항 서버통신을 위해 파라미터로 전달
    fun setData(kind: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")

        // 한달 전 Date
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -1)
        val startDate = sdf.format(cal.time)

        // 오늘 Date
        val date = Date(System.currentTimeMillis())
        val endDate = sdf.format(date)

        // 서버 통신을 위한 파라미터 전달
        getNotice(kind, startDate, endDate)
    }
}