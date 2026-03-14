package br.com.fabfdev.planner.presentation.ui

import android.content.DialogInterface
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import br.com.fabfdev.planner.R
import br.com.fabfdev.planner.databinding.FragmentDialogUpdatePlannerActivityBinding
import br.com.fabfdev.planner.domain.model.PlannerActivity
import br.com.fabfdev.planner.domain.utils.createCalendarFromTimeInMillis
import br.com.fabfdev.planner.domain.utils.toPlannerActivityDate
import br.com.fabfdev.planner.domain.utils.toPlannerActivityTime
import br.com.fabfdev.planner.presentation.ui.component.PlannerActivityDatePickerDialogFragment
import br.com.fabfdev.planner.presentation.ui.component.PlannerActivityTimePickerDialogFragment
import br.com.fabfdev.planner.presentation.ui.extension.hideKeyboard
import br.com.fabfdev.planner.presentation.ui.viewmodel.PlannerActivityViewModel
import br.com.fabfdev.planner.presentation.ui.viewmodel.SetDate
import br.com.fabfdev.planner.presentation.ui.viewmodel.SetTime
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdatePlannerActivityDialogFragment(
    private val selectedActivity: PlannerActivity,
) : BottomSheetDialogFragment() {

    private var _binding: FragmentDialogUpdatePlannerActivityBinding? = null
    private val binding get() = _binding!!

    private val plannerActivityViewModel: PlannerActivityViewModel by activityViewModels()

    override fun getTheme(): Int {
        return R.style.Theme_Planner_BottomSheet
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogUpdatePlannerActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plannerActivityViewModel.setSelectedActivity(selectedActivity)

        with(binding) {
            val selectedActivityDatetimeCalendar = createCalendarFromTimeInMillis(
                selectedActivity.datetime
            )

            tietUpdatedPlannerActivityName.setText(selectedActivity.name)
            tietUpdatedPlannerActivityDate.setText(
                selectedActivityDatetimeCalendar.toPlannerActivityDate()
            )
            tietUpdatedPlannerActivityTime.setText(
                selectedActivityDatetimeCalendar.toPlannerActivityTime()
            )

            tietUpdatedPlannerActivityName.doOnTextChanged { text, start, before, count ->
                if (text.toString().isEmpty()) {
                    tietUpdatedPlannerActivityName.clearFocus()
                    requireContext().hideKeyboard(fromView = tietUpdatedPlannerActivityName)
                }
                plannerActivityViewModel.updatedSelectedActivity(
                    name = text.toString()
                )
            }

            tietUpdatedPlannerActivityDate.setOnClickListener {
                PlannerActivityDatePickerDialogFragment(
                    initialDate = createCalendarFromTimeInMillis(selectedActivity.datetime),
                    onConfirm = { year, month, dayOfMonth ->
                        val filledCalendar = Calendar.getInstance().apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, month)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }
                        tietUpdatedPlannerActivityDate.setText(filledCalendar.toPlannerActivityDate())
                        plannerActivityViewModel.updatedSelectedActivity(
                            date = SetDate(year = year, month = month, dayOfMonth = dayOfMonth)
                        )
                    },
                    onCancel = {}
                ).show(childFragmentManager, PlannerActivityDatePickerDialogFragment.TAG)
            }

            tietUpdatedPlannerActivityTime.setOnClickListener {
                PlannerActivityTimePickerDialogFragment(
                    initialTime = createCalendarFromTimeInMillis(selectedActivity.datetime),
                    onConfirm = { hourOfDay, minute ->
                        val filledCalendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }
                        tietUpdatedPlannerActivityTime.setText(filledCalendar.toPlannerActivityTime())
                        plannerActivityViewModel.updatedSelectedActivity(
                            time = SetTime(hourOfDay = hourOfDay, minute = minute)
                        )
                    },
                    onCancel = {}
                ).show(childFragmentManager, PlannerActivityTimePickerDialogFragment.TAG)
            }

            tvUpdatedPlannerActivityDelete.setOnClickListener {
                plannerActivityViewModel.delete(
                    uuid = selectedActivity.uuid
                )
                dialog?.dismiss()
            }

            btnSaveUpdatedPlannerActivity.setOnClickListener {
                plannerActivityViewModel.saveUpdatedSelected()
                dialog?.dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        plannerActivityViewModel.clearSelectedActivity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "UpdatePlannerActivityDialogFragment"
    }

}