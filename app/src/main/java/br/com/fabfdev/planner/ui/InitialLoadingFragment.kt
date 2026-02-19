package br.com.fabfdev.planner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.fabfdev.planner.R
import br.com.fabfdev.planner.databinding.FragmentInitialLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InitialLoadingFragment : Fragment() {

    private var _binding: FragmentInitialLoadingBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy {
        findNavController()
    }

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

        with(binding) {
            lifecycleScope.launch {
                delay(1_500L)
                navController.navigate(R.id.action_initialLoadingFragment_to_userRegistrationFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}