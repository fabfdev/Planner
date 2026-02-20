package br.com.fabfdev.planner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.fabfdev.planner.R
import br.com.fabfdev.planner.databinding.FragmentInitialLoadingBinding
import br.com.fabfdev.planner.ui.viewmodel.UserRegistrationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InitialLoadingFragment : Fragment() {

    private var _binding: FragmentInitialLoadingBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy {
        findNavController()
    }

    private val userRegistrationViewModel by viewModels<UserRegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(1_500L)
            val navigateTo = if (userRegistrationViewModel.isUserRegistered()) {
                R.id.action_initialLoadingFragment_to_homeFragment
            } else {
                R.id.action_initialLoadingFragment_to_userRegistrationFragment
            }
            navController.navigate(navigateTo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}