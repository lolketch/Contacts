package com.example.feature_host.presentation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.core.SearchParams
import com.example.feature_list.presentation.DepartmentFragment


internal class DepartmentsViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val searchParam: SearchParams
) : FragmentStateAdapter(fm, lifecycle) {
    private val listDepartments = listOf(
        "all",
        "analytics",
        "android",
        "back_office",
        "backend",
        "design",
        "frontend",
        "hr",
        "ios",
        "management",
        "pr",
        "qa",
        "support"
    )

    override fun createFragment(position: Int): Fragment {
        val departmentFragment = DepartmentFragment()
        departmentFragment.arguments =
            bundleOf(
                DepartmentFragment.NAME_DEPARTMENT to listDepartments[position],
                DepartmentFragment.SEARCH_TEXT to searchParam
            )
        return departmentFragment
    }

    override fun getItemCount(): Int {
        return listDepartments.count()
    }
}