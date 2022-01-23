package com.example.trainee.ui.screens.screen_department_host

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.trainee.R
import com.example.trainee.utils.DepartmentsViewPagerAdapter
import com.example.trainee.utils.SearchListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_department_host.*

class DepartmentHostFragment : Fragment(R.layout.fragment_department_host) {
    private val tabsTitles by lazy(LazyThreadSafetyMode.NONE) {
        listOf(
            "All", "Analytics", "Android", "Back office",
            "Backend", "Design", "Frontend", "HR", "iOS", "Management", "PR", "QA", "Support"
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        val departmentTabLayout = view.findViewById<TabLayout>(R.id.departmentTabLayout)
        val departmentViewPager = view.findViewById<ViewPager2>(R.id.departmentViewPager)
        val adapter = DepartmentsViewPagerAdapter(childFragmentManager, lifecycle)

        departmentViewPager.adapter = adapter
        attachTabs(departmentTabLayout, departmentViewPager)

        searchEditText.addTextChangedListener {
            onSearchTextChanged(it)
        }
    }

    private fun attachTabs(departmentTabLayout: TabLayout, departmentViewPager: ViewPager2) {
        TabLayoutMediator(departmentTabLayout, departmentViewPager) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    private fun onSearchTextChanged(editable: Editable?) {
        val searchText = editable?.toString() ?: ""
        doWithSelectedPage {
            it.onSearchTextChanged(searchText)
        }
    }
    private fun doWithSelectedPage(action: (SearchListener) -> Unit) {
        childFragmentManager
            .findFragmentByTag("f" + departmentViewPager.currentItem)
            ?.let { selectedPage ->
                if (selectedPage is SearchListener) {
                    action.invoke(selectedPage)
                }
            }
    }
}