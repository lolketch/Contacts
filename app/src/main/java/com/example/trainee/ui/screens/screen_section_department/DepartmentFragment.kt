package com.example.trainee.ui.screens.screen_section_department

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainee.R
import com.example.trainee.base.App
import com.example.trainee.data.model.User
import com.example.trainee.ui.screens.screen_department_host.DepartmentHostFragment
import com.example.trainee.ui.screens.screen_profile.ProfileFragment
import com.example.trainee.utils.*
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DepartmentFragment : Fragment(R.layout.fragment_department), SearchListener {
    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory
    lateinit var departmentViewModel: DepartmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DepartmentAdapter
    private lateinit var loaderView: ProgressBar
    private lateinit var testSuggestion: TextView
    private var nameDepartment: String? = null
    private var searchText: String = ""
    private val listUsers: MutableList<User> = mutableListOf()

    companion object {
        const val NAME_DEPARTMENT = "NAME_DEPARTMENT"
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(departmentFragment = this)
        departmentViewModel =
            ViewModelProvider(this, multiViewModelFactory)[DepartmentViewModel::class.java]
        nameDepartment = arguments?.get(NAME_DEPARTMENT).toString()
        searchText = (arguments?.get(SEARCH_TEXT) as SearchParams).searchText
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("onViewCreated", "$nameDepartment")
        testSuggestion = view.findViewById(R.id.testSuggestion)
        loaderView = view.findViewById(R.id.loaderView)
        recyclerView = view.findViewById(R.id.departmentRecyclerView)
        adapter = DepartmentAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = adapter
//        }
        observeSearchList()
        observeViewState()

        adapter.attachClicks(object : UserListAdapterClicks {
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
                recyclerView.visibility = View.GONE
                loaderView.visibility = View.VISIBLE
                testSuggestion.visibility = View.GONE
            }

            is UsersListViewState.Success -> {
                if (nameDepartment == "all") listUsers.addAll(viewState.items)
                else listUsers.addAll(viewState.items.filter { it.department == nameDepartment })
                if (searchText.isNotEmpty()) departmentViewModel.onSearchTextChanged(
                    searchText,
                    listUsers
                )
                else adapter.submitList(listUsers)

                testSuggestion.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                loaderView.visibility = View.GONE
            }

            is UsersListViewState.Error -> {
                view?.let { onSnackView(it) }
                testSuggestion.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                loaderView.visibility = View.GONE
            }
        }
    }

    fun onSnackView(view: View){
        val snackbar = Snackbar.make(view, "Не могу обновить данные.\nПроверь соединение с интернетом.",
            Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(),R.color.error))
        val textView =
            snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 16f
        snackbar.show()
    }

    private fun observeViewState() {
        departmentViewModel.viewState.observe(viewLifecycleOwner, {
            bindViewState(it)
        })
    }

    private fun observeSearchList() {
        departmentViewModel.searchList.observe(viewLifecycleOwner, { listUsers ->
            adapter.submitList(listUsers) {
                recyclerView.scrollToPosition(0)
            }
        })
    }

    override fun onPageSelected(searchText: String) {
        departmentViewModel.onSearchTextChanged(searchText, listUsers)
    }

    override fun onSearchTextChanged(searchText: String) {
        departmentViewModel.onSearchTextChanged(searchText, listUsers)
    }
}