package com.example.trainee.ui.screens.screen_department_host

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.databinding.FragmentDepartmentHostBinding
import com.example.trainee.utils.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_department_host.*
import javax.inject.Inject

class DepartmentHostFragment : Fragment(R.layout.fragment_department_host) {
    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private lateinit var viewModel: DepartmentHostViewModel

    private var _binding: FragmentDepartmentHostBinding? = null
    private val binding get() = _binding!!

    private var adapterViewPager: DepartmentsViewPagerAdapter? = null

    private val tabsTitles by lazy(LazyThreadSafetyMode.NONE) {
        listOf(
            "All", "Analytics", "Android", "Back office",
            "Backend", "Design", "Frontend", "HR", "iOS", "Management", "PR", "QA", "Support"
        )
    }
    private val viewPagerCallback by lazy() {
        object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                this@DepartmentHostFragment.onPageSelected()
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> binding.swipeRefresh.isEnabled = true
                    ViewPager2.SCROLL_STATE_DRAGGING -> binding.swipeRefresh.isEnabled = false
                    ViewPager2.SCROLL_STATE_SETTLING -> binding.swipeRefresh.isEnabled = false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(departmentHostFragment = this)
        viewModel = ViewModelProvider(this, viewModelFactory)[DepartmentHostViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDepartmentHostBinding.bind(view)

        val searchParams = SearchParams(searchText = binding.searchEditText.text.toString())
        adapterViewPager = DepartmentsViewPagerAdapter(childFragmentManager, lifecycle, searchParams)

        binding.viewPager.apply {
            registerOnPageChangeCallback(viewPagerCallback)
            this.adapter = adapterViewPager
        }

        attachTabs()
        setSearchEditTextListener(searchParams = searchParams)
        setRefreshListener()
        observeConnection()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapterViewPager = null
        _binding = null
    }

    private fun setRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.viewPager.isUserInputEnabled = false
            onRefresh()
        }
    }

    private fun setSearchEditTextListener(searchParams: SearchParams) {
        binding.searchEditText.apply {
            addTextChangedListener {
                searchParams.searchText = it.toString()
                onSearchTextChanged(it)
            }
            setOnFocusChangeListener { _, _ ->
                binding.imageSearchTool.setColorFilter(Color.BLACK)
            }
        }
    }

    private fun observeConnection() {
        viewModel.connection.observe(viewLifecycleOwner, {
            if (it == false) {
                view?.postDelayed({
                    findNavController().navigate(
                        R.id.action_departmentHostFragment_to_errorFragment
                    )
                }, 500)
            }
        })
    }

    private fun attachTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    private fun onPageSelected() {
        doWithSelectedPage {
            it.onPageSelected(binding.searchEditText.text.toString())
        }
    }

    private fun onRefresh() {
        doWithSelectedPage {
            it.onRefresh()
        }
        binding.viewPager.isUserInputEnabled = true
        binding.swipeRefresh.setRefreshing(false)
    }

    private fun onSearchTextChanged(editable: Editable?) {
        val searchText = editable?.toString() ?: ""
        doWithSelectedPage {
            it.onSearchTextChanged(searchText)
        }
    }

    private fun doWithSelectedPage(action: (SearchListener) -> Unit) {
        childFragmentManager
            .findFragmentByTag("f" + viewPager.currentItem)
            ?.let { selectedPage ->
                if (selectedPage is SearchListener) {
                    action.invoke(selectedPage)
                }
            }
    }
}