package br.com.fabfdev.planner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.fabfdev.planner.R
import br.com.fabfdev.planner.databinding.FragmentUserRegistrationBinding

class UserRegistrationFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSaveUser.setOnClickListener {
                navController.navigate(R.id.action_userRegistrationFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}