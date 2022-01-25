package com.example.trainee.ui.screens.screen_department_host

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.ui.screens.screen_section_department.DepartmentViewModel
import com.example.trainee.utils.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_department_host.*
import javax.inject.Inject

class DepartmentHostFragment : Fragment(R.layout.fragment_department_host) {
    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory
    lateinit var departmentHostViewModel: DepartmentHostViewModel

    private val tabsTitles by lazy(LazyThreadSafetyMode.NONE) {
        listOf(
            "All", "Analytics", "Android", "Back office",
            "Backend", "Design", "Frontend", "HR", "iOS", "Management", "PR", "QA", "Support"
        )
    }
    private val viewPagerChangeCallback by lazy() {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                this@DepartmentHostFragment.onPageSelected()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(departmentHostFragment = this)
        departmentHostViewModel =
            ViewModelProvider(this, multiViewModelFactory)[DepartmentHostViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        val departmentTabLayout = view.findViewById<TabLayout>(R.id.departmentTabLayout)
        val departmentViewPager = view.findViewById<ViewPager2>(R.id.departmentViewPager)
        val searchParams = SearchParams(searchText = searchEditText.text.toString())
        val adapter = DepartmentsViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            searchParams
        )

        departmentViewPager.offscreenPageLimit = 3
        departmentViewPager.registerOnPageChangeCallback(viewPagerChangeCallback)
        departmentViewPager.adapter = adapter
        attachTabs(departmentTabLayout, departmentViewPager)

        searchEditText.addTextChangedListener {
            searchParams.searchText = it.toString()
            onSearchTextChanged(it)
        }
    }

    private fun attachTabs(departmentTabLayout: TabLayout, departmentViewPager: ViewPager2) {
        TabLayoutMediator(departmentTabLayout, departmentViewPager) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    private fun onPageSelected() {
        doWithSelectedPage {
            it.onPageSelected(searchEditText.text.toString())
        }
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