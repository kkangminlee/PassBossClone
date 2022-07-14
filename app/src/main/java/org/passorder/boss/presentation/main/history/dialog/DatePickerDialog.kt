package org.passorder.boss.presentation.main.history.dialog

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import org.passorder.boss.R
import org.passorder.boss.databinding.DialogPickerBinding
import java.util.*

class DatePickerDialog : DialogFragment() {
    private var _binding: DialogPickerBinding? = null
    private val binding: DialogPickerBinding
        get() = requireNotNull(_binding)
    private lateinit var cal: Calendar

    fun interface DateListener {
        fun onClick(start: String, end: String)
    }

    private var dataListener: DateListener? = null

    fun onDateListener(dateListener: DateListener) {
        this.dataListener = dateListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPickerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initEvent()
    }

    private fun initView() {
        val deviceWidth = Resources.getSystem().displayMetrics.widthPixels
        val dialogHorizontalMargin = (Resources.getSystem().displayMetrics.density * 16) * 2
        dialog?.window?.apply {
            setBackgroundDrawableResource(R.drawable.shape_bg_dialog)
            setLayout(
                (deviceWidth - dialogHorizontalMargin).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun initData() {
        cal = Calendar.getInstance()

        binding.npYear.apply {
            minValue = 2013
            maxValue = cal[Calendar.YEAR]
            value = cal[Calendar.YEAR]
        }

        binding.npMonth.apply {
            minValue = 1
            maxValue = 12
            value = cal[Calendar.MONTH] + 1
        }
    }

    private fun initEvent() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnOk.setOnClickListener {
            cal.apply {
                set(Calendar.YEAR, binding.npYear.value)
                set(Calendar.MONTH, binding.npMonth.value - 1)
            }
            val yearMonth = "${cal[Calendar.YEAR]}-${String.format("%02d",cal[Calendar.MONTH]+1)}"
            val startDate = "${yearMonth}-${String.format("%02d",cal.getActualMinimum(Calendar.DAY_OF_MONTH))}"
            val endDate = "${yearMonth}-${String.format("%02d",cal.getActualMaximum(Calendar.DAY_OF_MONTH))}"
            dataListener?.onClick(startDate,endDate)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}