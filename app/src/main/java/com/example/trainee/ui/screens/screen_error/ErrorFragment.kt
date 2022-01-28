package com.example.trainee.ui.screens.screen_error

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.utils.MultiViewModelFactory
import javax.inject.Inject

class ErrorFragment : Fragment(R.layout.fragment_error) {
    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory
    lateinit var errorViewModel: ErrorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(errorFragment = this)
        errorViewModel =
            ViewModelProvider(this, multiViewModelFactory)[ErrorViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorViewModel.connection.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(R.id.action_errorFragment_to_departmentHostFragment)
            }
        })
        view.findViewById<TextView>(R.id.try_again_title).setOnClickListener {
            errorViewModel.checkConnection()
        }
    }
}