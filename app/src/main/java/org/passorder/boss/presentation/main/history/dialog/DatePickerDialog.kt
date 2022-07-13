package org.passorder.boss.presentation.main.history.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
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
        initData()
        initEvent()
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

            val startDate = "${yearMontyDate()}-${String.format("%02d",cal.getActualMinimum(Calendar.DAY_OF_MONTH))}"
            val endDate = "${yearMontyDate()}-${String.format("%02d",cal.getActualMaximum(Calendar.DAY_OF_MONTH))}"
            dataListener?.onClick(startDate,endDate)
            dismiss()
        }
    }

    private fun yearMontyDate(): String {
        return "${binding.npYear.value}-${String.format("%02d", binding.npMonth.value)}"
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}