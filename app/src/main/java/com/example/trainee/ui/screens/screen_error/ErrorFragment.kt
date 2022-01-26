package com.example.trainee.ui.screens.screen_error

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.trainee.R

class ErrorFragment : Fragment(R.layout.fragment_error) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.try_again_title).setOnClickListener {
            findNavController().navigate(R.id.action_errorFragment_to_departmentHostFragment)
        }
    }
}