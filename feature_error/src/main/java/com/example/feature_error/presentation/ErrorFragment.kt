package com.example.feature_error.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.api.MultiViewModelFactory
import com.example.feature_error.di.FeatureErrorComponentViewModel
import com.example.feature_error.R
import com.example.feature_error.databinding.FragmentErrorBinding
import dagger.Lazy
import javax.inject.Inject

class ErrorFragment : Fragment(R.layout.fragment_error) {
    @Inject
    internal lateinit var viewModelFactory: Lazy<ErrorViewModel.Factory>
    private val viewModel: ErrorViewModel by viewModels {
        viewModelFactory.get()
    }

    private var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<FeatureErrorComponentViewModel>()
            .newComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentErrorBinding.bind(view)

        observerConnection()

        binding.tryAgainTitle.setOnClickListener {
            viewModel.checkConnection()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observerConnection() {
        viewModel.connection.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(R.id.action_errorFragment_to_departmentHostFragment)
            }
        })
    }
}