package br.com.fabfdev.planner.presentation.ui.component

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import br.com.fabfdev.planner.R

class PlannerActivityDatePickerDialogFragment(
    private val initialDate: Calendar? = null,
    private val onConfirm: (Int, Int, Int) -> Unit,
    private val onCancel: () -> Unit,
): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = initialDate ?: Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val customDatePickerDialog =
            DatePickerDialog(
                requireContext(),
                R.style.Theme_Planner_DatePickerDialog,
                this,
                year,
                month,
                day
            ).setupPlannerDatePicker(
                minDate = calendar.timeInMillis
            )

        return customDatePickerDialog
    }

    private fun DatePickerDialog.setupPlannerDatePicker(minDate: Long): DatePickerDialog =
        this.apply {
            datePicker.minDate = minDate

            setButton(
                DialogInterface.BUTTON_POSITIVE,
                getString(R.string.confirmar)
            ) { _, _ ->
                onConfirm(
                    datePicker.year,
                    datePicker.month,
                    datePicker.dayOfMonth
                )
            }

            setButton(
                DialogInterface.BUTTON_NEGATIVE,
                getString(R.string.cancelar)
            ) { _, _ ->
                onCancel()
            }
        }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        // Obs.: So seria utilizado caso nao houvesse a
        // sobrescrita do botao primario de confirmacao
    }

    companion object {
        const val TAG = "PlannerActivityDatePickerDialogFragment"
    }

}