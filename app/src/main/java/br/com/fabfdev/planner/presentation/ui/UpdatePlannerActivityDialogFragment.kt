package br.com.fabfdev.planner.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.fabfdev.planner.R
import br.com.fabfdev.planner.databinding.FragmentUpdatePlannerActivityDialogBinding
import br.com.fabfdev.planner.domain.model.PlannerActivity
import br.com.fabfdev.planner.domain.utils.createCalendarFromTimeInMillis
import br.com.fabfdev.planner.domain.utils.toPlannerActivityDate
import br.com.fabfdev.planner.domain.utils.toPlannerActivityTime
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdatePlannerActivityDialogFragment(
    private val selectedActivity: PlannerActivity,
) : BottomSheetDialogFragment() {

    private var _binding: FragmentUpdatePlannerActivityDialogBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int {
        return R.style.Theme_Planner_BottomSheet
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePlannerActivityDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "UpdatePlannerActivityDialogFragment"
    }

}