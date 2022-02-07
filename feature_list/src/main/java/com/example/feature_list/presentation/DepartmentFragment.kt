package com.example.feature_list.presentation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api.UserDto
import com.example.core.BaseFragment
import com.example.core.BaseViewModelFactory
import com.example.core.SearchListener
import com.example.core.Constance.USER_DATA
import com.example.core.SearchParams
import com.example.feature_list.*
import com.example.feature_list.di.FeatureDepartmentComponentViewModel
import com.example.feature_list.databinding.FragmentDepartmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import javax.inject.Inject

class DepartmentFragment : BaseFragment<FragmentDepartmentBinding>(), SearchListener {
    @Inject
    internal lateinit var viewModelFactory: Lazy<BaseViewModelFactory<DepartmentViewModel>>
    private val viewModel: DepartmentViewModel by viewModels {
        viewModelFactory.get()
    }

    private var searchText = ""
    private var nameDepartment = ""
    private val listUsers: MutableList<UserDto> = mutableListOf()

    private var departmentAdapter: DepartmentAdapter? = null

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDepartmentBinding = FragmentDepartmentBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<FeatureDepartmentComponentViewModel>()
            .newComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        departmentAdapter = DepartmentAdapter()
        initUi()
        observeSearchList()
        observeViewState()
        attachClicksAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        departmentAdapter = null
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
        departmentAdapter?.attachClicks(NavigateAdapterClicks(findNavController()))
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
            departmentAdapter?.submitList(listUsers)
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
            departmentAdapter?.submitList(listUsers) {
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

class NavigateAdapterClicks(private val navController: NavController) : UserListAdapterClicks {
    override fun onItemClick(model: UserDto) {
        navController.navigate(
            R.id.action_departmentHostFragment_to_profileFragment,
            bundleOf(USER_DATA to model)
        )
    }
}