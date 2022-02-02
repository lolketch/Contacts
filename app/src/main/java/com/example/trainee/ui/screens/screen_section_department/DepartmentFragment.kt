package com.example.trainee.ui.screens.screen_section_department

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.data.model.User
import com.example.trainee.databinding.FragmentDepartmentBinding
import com.example.trainee.ui.screens.screen_profile.ProfileFragment
import com.example.trainee.utils.*
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DepartmentFragment : Fragment(R.layout.fragment_department), SearchListener {
    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory
    private lateinit var viewModel: DepartmentViewModel

    private var _binding: FragmentDepartmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchText: String
    private lateinit var nameDepartment: String
    private val listUsers: MutableList<User> = mutableListOf()

    private val departmentAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DepartmentAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(departmentFragment = this)
        viewModel = ViewModelProvider(this, multiViewModelFactory)[DepartmentViewModel::class.java]
        getArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDepartmentBinding.bind(view)

        initUi()
        observeSearchList()
        observeViewState()
        attachClicksAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = departmentAdapter
        }
    }

    private fun getArgs() {
        nameDepartment = arguments?.get(NAME_DEPARTMENT).toString()
        searchText = (arguments?.get(SEARCH_TEXT) as SearchParams).searchText
    }

    private fun attachClicksAdapter() {
        departmentAdapter.attachClicks(object : UserListAdapterClicks {
            override fun onItemClick(model: User) {
                findNavController().navigate(
                    R.id.action_departmentHostFragment_to_profileFragment,
                    bundleOf(ProfileFragment.USER_DATA to model)
                )
            }
        })
    }

    private fun bindViewState(viewState: UsersListViewState) {
        when (viewState) {

            is UsersListViewState.Loading -> {
                binding.userRecyclerView.visibility = View.GONE
                binding.loaderView.visibility = View.VISIBLE
            }

            is UsersListViewState.Success -> {
                addItemsToList(viewState)
                checkSearchText()
                binding.userRecyclerView.visibility = View.VISIBLE
                binding.loaderView.visibility = View.GONE
            }

            is UsersListViewState.Error -> {
                onSnackView(requireView(), getString(R.string.snack_error), "#FFF44336")
                binding.userRecyclerView.visibility = View.GONE
                binding.loaderView.visibility = View.GONE
            }
        }
    }

    private fun checkSearchText() {
        if (searchText.isNotEmpty()) {
            viewModel.onSearchTextChanged(searchText, listUsers)
        } else {
            departmentAdapter.submitList(listUsers)
        }
        searchText = ""
    }

    private fun addItemsToList(viewState: UsersListViewState.Success) {
        listUsers.clear()
        if (nameDepartment == "all") {
            listUsers.addAll(viewState.items)
        } else {
            listUsers.addAll(viewState.items.filter { it.department == nameDepartment })
        }
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            bindViewState(it)
        })
    }

    private fun observeSearchList() {
        viewModel.searchList.observe(viewLifecycleOwner, { listUsers ->
            departmentAdapter.submitList(listUsers) {
                binding.userRecyclerView.scrollToPosition(0)
            }
        })
    }

    override fun onPageSelected(searchText: String) {
        viewModel.onSearchTextChanged(searchText, listUsers)
    }

    override fun onSearchTextChanged(searchText: String) {
        viewModel.onSearchTextChanged(searchText, listUsers)
    }

    override fun onRefresh() {
        viewModel.fetchUsers()
        onSnackView(requireView(), getString(R.string.snack_refresh), "#FF6534FF")
    }

    private fun onSnackView(view: View, text: String, backColor: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(Color.parseColor(backColor))
            .setTextColor(Color.WHITE)
            .show()
    }

    companion object {
        const val NAME_DEPARTMENT = "NAME_DEPARTMENT"
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }
}