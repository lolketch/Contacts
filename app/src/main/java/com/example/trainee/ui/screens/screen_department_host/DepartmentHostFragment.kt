package com.example.trainee.ui.screens.screen_department_host

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.utils.MultiViewModelFactory
import javax.inject.Inject

class DepartmentHostFragment : Fragment(R.layout.fragment_department_host) {
    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory
    lateinit var departmentHostViewModel: DepartmentHostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(departmentHostFragment = this)
        departmentHostViewModel =
            ViewModelProvider(this, multiViewModelFactory)[DepartmentHostViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}