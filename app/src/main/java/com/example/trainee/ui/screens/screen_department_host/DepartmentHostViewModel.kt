package com.example.trainee.ui.screens.screen_department_host

import androidx.lifecycle.ViewModel
import com.example.trainee.utils.SearchParams
import javax.inject.Inject

class DepartmentHostViewModel @Inject constructor() :ViewModel() {
    val searchParams = SearchParams(searchText = "")
}