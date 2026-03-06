package br.com.fabfdev.planner.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import br.com.fabfdev.planner.R
import br.com.fabfdev.planner.domain.utils.imageBase64ToBitmap
import br.com.fabfdev.planner.databinding.FragmentHomeBinding
import br.com.fabfdev.planner.presentation.ui.component.PlannerActivityAdapter
import br.com.fabfdev.planner.presentation.ui.component.PlannerActivityDatePickerDialogFragment
import br.com.fabfdev.planner.presentation.ui.component.PlannerActivityTimePickerDialogFragment
import br.com.fabfdev.planner.presentation.ui.viewmodel.PlannerActivityViewModel
import br.com.fabfdev.planner.presentation.ui.viewmodel.UserRegistrationViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val userRegistrationViewModel: UserRegistrationViewModel by activityViewModels()
    val plannerActivityViewModel: PlannerActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            rvPlannerActivities.adapter = PlannerActivityAdapter()
            plannerActivityViewModel.fetchActivities()

            tietNewPlannerActivityDate.setOnClickListener {
                PlannerActivityDatePickerDialogFragment(
                    onConfirm = { year, month, day ->
                        println("$year $month $day")
                    },
                    onCancel = {}
                ).show(childFragmentManager, PlannerActivityDatePickerDialogFragment.TAG)
            }

            tietNewPlannerActivityTime.setOnClickListener {
                PlannerActivityTimePickerDialogFragment(
                    onConfirm = { hour, minute ->
                        println("$hour, $minute")
                    },
                    onCancel = {}
                ).show(childFragmentManager, PlannerActivityTimePickerDialogFragment.TAG)
            }

            btnSaveNewPlannerActivity.setOnClickListener {
                UpdatePlannerActivityDialogFragment()
                    .show(
                        childFragmentManager,
                        UpdatePlannerActivityDialogFragment.TAG
                    )
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            launch {
                userRegistrationViewModel.profile.collect { profile ->
                    binding.tvUserName.text = getString(
                        R.string.ola_usuario,
                        profile.name
                    )
                    imageBase64ToBitmap(profile.image)?.let {
                        binding.ivUserPhoto.setImageBitmap(it)
                    }
                }
            }
            launch {
                userRegistrationViewModel.isTokenValid.distinctUntilChanged { old, new ->
                    old == new
                }.collect { isValid ->
                    if (isValid == false) {
                        showNewTokenSnackBar()
                    }
                }
            }
            launch {
                plannerActivityViewModel.activities.collect { activities ->
                    with(binding) {
                        if (rvPlannerActivities.adapter == null) {
                            rvPlannerActivities.adapter = PlannerActivityAdapter()
                        }
                        (rvPlannerActivities.adapter as PlannerActivityAdapter).submitList(
                            activities
                        )
                    }
                }
            }
        }
    }

    private fun showNewTokenSnackBar() {
        Snackbar.make(
            requireView(),
            "Ops… O seu token expirou",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Obter novo token") {
                userRegistrationViewModel.obtainNewToken()
            }
            .setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.lime_300
                )
            )
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}