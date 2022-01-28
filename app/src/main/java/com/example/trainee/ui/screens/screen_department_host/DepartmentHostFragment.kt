package com.example.trainee.ui.screens.screen_department_host

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.marginTop
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.ui.screens.screen_section_department.DepartmentFragment
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
    private lateinit var searchEditText: EditText
    private lateinit var departmentTabLayout: TabLayout
    private lateinit var departmentViewPager: ViewPager2
    private lateinit var imageSearchTool: ImageView

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

        searchEditText = view.findViewById(R.id.searchEditText)
        departmentTabLayout = view.findViewById(R.id.departmentTabLayout)
        departmentViewPager = view.findViewById(R.id.departmentViewPager)
        imageSearchTool = view.findViewById(R.id.imageSearchTool)

        val searchParams = SearchParams(searchText = searchEditText.text.toString())
        val adapter = DepartmentsViewPagerAdapter(childFragmentManager, lifecycle, searchParams)
        departmentViewPager.registerOnPageChangeCallback(viewPagerChangeCallback)
        departmentViewPager.adapter = adapter
        attachTabs(departmentTabLayout, departmentViewPager)

        setSearchEditTextListener(searchParams = searchParams)

        observeConnection()
    }

    private fun setSearchEditTextListener(searchParams: SearchParams) {
        searchEditText.addTextChangedListener {
            searchParams.searchText = it.toString()
            onSearchTextChanged(it)
        }
        searchEditText.setOnFocusChangeListener { _, _ ->
            imageSearchTool.setColorFilter(
                Color.BLACK
            )
        }
    }

    private fun observeConnection() {
        departmentHostViewModel.connection.observe(viewLifecycleOwner, {
            if (it == false) {
                view?.postDelayed({
                    findNavController().navigate(R.id.action_departmentHostFragment_to_errorFragment)
                }, 500)
            }
        })
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