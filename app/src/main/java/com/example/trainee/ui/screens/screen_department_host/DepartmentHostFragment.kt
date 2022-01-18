package com.example.trainee.ui.screens.screen_department_host

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.trainee.R

class DepartmentHostFragment: Fragment(R.layout.fragment_department_host) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Fragment","onCreated")
    }
}