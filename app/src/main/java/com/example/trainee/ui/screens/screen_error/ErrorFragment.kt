package com.example.trainee.ui.screens.screen_error

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.databinding.FragmentErrorBinding
import com.example.trainee.utils.MultiViewModelFactory
import javax.inject.Inject

class ErrorFragment : Fragment(R.layout.fragment_error) {
    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    lateinit var viewModel: ErrorViewModel

    private var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(errorFragment = this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ErrorViewModel::class.java]
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