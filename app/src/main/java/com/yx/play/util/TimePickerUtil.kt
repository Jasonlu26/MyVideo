package com.yx.play.util

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import java.text.SimpleDateFormat
import java.util.*

/**
 * @description
 * @time 2022/1/6 4:49 下午
 * @version
 */
object TimePickerUtil {

    private var timePicker: TimePickerView? = null

    fun showPick(context: Context): TimePickerUtil {
        val calendarStart: Calendar = Calendar.getInstance()
        val minYear = 2000
        val minMonth = 0
        calendarStart.set(minYear, minMonth, 1)

        val calendarEnd: Calendar = Calendar.getInstance()
        val maxYear = calendarEnd.get(Calendar.YEAR)
        val maxMonth = calendarEnd.get(Calendar.MONTH)
        calendarEnd.set(maxYear, maxMonth, 1)

        timePicker = TimePickerBuilder(context, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date, v: View?) {


                val format: SimpleDateFormat = SimpleDateFormat("yyyy-MM");
                val datText = format.format(date)
                val year = datText.substringBefore("-")
                val month = datText.substringAfter("-")
                _complete?.invoke(
                    year,
                    month,
                    ""
                )
            }
        })
//            .setLayoutRes(R.layout.dialog_time_picker) { view ->
//                val tvSubmit = view.findViewById<AppCompatButton>(R.id.okBtn)
//                val tvCancel = view.findViewById<AppCompatButton>(R.id.cancelBtn)
//                tvSubmit.click {
//                    timePicker?.returnData()
//                    timePicker?.dismiss()
//                }
//
//                tvCancel.click {
//                    timePicker?.dismiss()
//                }
//            }

            .setDate(calendarEnd)
            .setRangDate(calendarStart, calendarEnd)
            .setContentTextSize(16)
            .setItemVisibleCount(5)
//            .setOutSideColor(R.color.transparent.getColor())
            .setType(booleanArrayOf(true, true, false, false, false, false))
            .setLabel("", "", "", "", "", "")
            .setLineSpacingMultiplier(3.0f)
            .setTextXOffset(0, 0, 0, 40, 0, -40)
            .isCenterLabel(false)
//            .setDividerColor(R.color.color_DADCE7.getColor())
            .isDialog(true)
            .build()

//        timePicker?.findViewById(R.id.rootPickerView)
//            ?.setBackgroundResource(R.drawable.shape_round_25_top_rect_bg_by_white)
        val mDialog: Dialog? = timePicker?.dialog
        if (mDialog != null) {
            val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
            params.leftMargin = 0
            params.rightMargin = 0
            timePicker?.dialogContainerLayout?.layoutParams = params
            val dialogWindow: Window? = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)
                dialogWindow.setGravity(Gravity.BOTTOM)
                dialogWindow.setDimAmount(0.3f)
            }
        }

        timePicker?.show()

        return this
    }


    fun showDatePick(context: Context, currentCalendar: Calendar? = null): TimePickerUtil {
        val calendarStart: Calendar = Calendar.getInstance()
        val minYear = 1900
        val minMonth = 0
        calendarStart.set(minYear, minMonth, 1)


        val calendarEnd: Calendar = Calendar.getInstance()
        val maxYear = calendarEnd.get(Calendar.YEAR)
        val maxMonth = calendarEnd.get(Calendar.MONTH)
        val maxDay = calendarEnd.get(Calendar.DATE)
        calendarEnd.set(maxYear, maxMonth, maxDay)

        val calendar = currentCalendar ?: calendarEnd

        timePicker = TimePickerBuilder(context) { date, _ ->
            val format = SimpleDateFormat("yyyy-MM-dd");
            val datText = format.format(date)

            val dates = datText.split("-")
            val year = dates.elementAtOrNull(0) ?: ""
            val month = dates.elementAtOrNull(1) ?: ""
            val day = dates.elementAtOrNull(2) ?: ""
            _complete?.invoke(
                year,
                month,
                day
            )
        }
//            .setLayoutRes(R.layout.dialog_time_picker) { view ->
//                val tvSubmit = view.findViewById<AppCompatButton>(R.id.okBtn)
//                val tvCancel = view.findViewById<AppCompatButton>(R.id.cancelBtn)
//                tvSubmit.click {
//                    timePicker?.returnData()
//                    timePicker?.dismiss()
//                }
//
//                tvCancel.click {
//                    timePicker?.dismiss()
//                }
//            }

            .setDate(calendar)
            .setRangDate(calendarStart, calendarEnd)
            .setContentTextSize(16)
            .setItemVisibleCount(5)
//            .setOutSideColor(R.color.transparent.getColor())
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setLabel("", "", "", "", "", "")
            .setLineSpacingMultiplier(3.0f)
            .setTextXOffset(0, 0, 0, 40, 0, -40)
            .isCenterLabel(false)
//            .setDividerColor(R.color.color_DADCE7.getColor())
            .isDialog(true)
            .build()

//        timePicker?.findViewById(R.id.rootPickerView)
//            ?.setBackgroundResource(R.drawable.shape_round_25_top_rect_bg_by_white)
        val mDialog: Dialog? = timePicker?.dialog
        if (mDialog != null) {
            val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
            params.leftMargin = 0
            params.rightMargin = 0
            timePicker?.dialogContainerLayout?.layoutParams = params
            val dialogWindow: Window? = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)
                dialogWindow.setGravity(Gravity.BOTTOM)
                dialogWindow.setDimAmount(0.3f)
            }
        }

        timePicker?.show()

        return this
    }

    private var _complete: ((String, String, String) -> Unit)? = null
    fun onComplete(complete: (String, String, String) -> Unit) {
        _complete = complete
    }
}