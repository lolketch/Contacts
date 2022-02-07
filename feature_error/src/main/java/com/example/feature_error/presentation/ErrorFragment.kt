package com.example.feature_error.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModelFactory
import com.example.feature_error.di.FeatureErrorComponentViewModel
import com.example.feature_error.R
import com.example.feature_error.databinding.FragmentErrorBinding
import dagger.Lazy
import javax.inject.Inject

class ErrorFragment : BaseFragment<FragmentErrorBinding>() {
    @Inject
    internal lateinit var viewModelFactory: Lazy<BaseViewModelFactory<ErrorViewModel>>
    private val viewModel: ErrorViewModel by viewModels {
        viewModelFactory.get()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentErrorBinding = FragmentErrorBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<FeatureErrorComponentViewModel>()
            .newComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerConnection()

        binding.tryAgainTitle.setOnClickListener {
            viewModel.checkConnection()
        }
    }

    private fun observerConnection() {
        viewModel.connection.observe(viewLifecycleOwner, { connection ->
            if (connection == true) {
                findNavController().navigate(R.id.action_errorFragment_to_departmentHostFragment)
            }
        })
    }
}