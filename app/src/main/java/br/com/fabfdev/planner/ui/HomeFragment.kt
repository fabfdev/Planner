package br.com.fabfdev.planner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.fabfdev.planner.R
import br.com.fabfdev.planner.data.utils.imageBase64ToBitmap
import br.com.fabfdev.planner.databinding.FragmentHomeBinding
import br.com.fabfdev.planner.ui.viewmodel.UserRegistrationViewModel
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val userRegistrationViewModel: UserRegistrationViewModel by viewModels()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}